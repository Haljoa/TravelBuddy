import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import static spark.Spark.*;

import java.util.*;

public class TransportSystem {
    private static MongoCollection<Document> journeys;
    private static MongoCollection<Document> journeysExtraInfo;
    private static MongoCollection<Document> agencies;
    private static MongoCollection<Document> stops;
    private static Gson gson = new Gson();
    private static JsonWriterSettings jsonSettings = JsonWriterSettings.builder()
        .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
        .build();
    
    public static void main(String[] args) {
        // Koble til MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("transportdb");
        
        journeys = database.getCollection("journeys");
        journeysExtraInfo = database.getCollection("journeys_extra_info");
        agencies = database.getCollection("agencies");
        stops = database.getCollection("stops");
        
        // Server port
        port(8080);
        
        // CORS for frontend
        after((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "*");
        });
        
        // === JOURNEYS MED EXTRA INFO ===
        get("/api/journeys", (req, res) -> {
            res.type("application/json");
            List<Map<String, Object>> results = new ArrayList<>();
            
            try (MongoCursor<Document> cursor = journeys.find().limit(50).iterator()) {
                while (cursor.hasNext()) {
                    Document journey = cursor.next();
                    Map<String, Object> journeyWithExtra = getJourneyWithExtraInfo(journey);
                    results.add(journeyWithExtra);
                }
            }
            return gson.toJson(results);
        });
        
        // Hent journeys etter type (BUS, TRAIN, BOAT)
        get("/api/journeys/type/:type", (req, res) -> {
            res.type("application/json");
            String type = req.params("type").toUpperCase();
            List<Map<String, Object>> results = new ArrayList<>();
            
            try (MongoCursor<Document> cursor = journeys.find(
                regex("routeId", "^" + type + "_")).iterator()) {
                while (cursor.hasNext()) {
                    Document journey = cursor.next();
                    Map<String, Object> journeyWithExtra = getJourneyWithExtraInfo(journey);
                    results.add(journeyWithExtra);
                }
            }
            return gson.toJson(results);
        });
        
        // === AGENCIES ===
        get("/api/agencies", (req, res) -> {
            res.type("application/json");
            List<Document> results = new ArrayList<>();
            try (MongoCursor<Document> cursor = agencies.find().iterator()) {
                while (cursor.hasNext()) {
                    results.add(cursor.next());
                }
            }
            return gson.toJson(results);
        });
        
        // === STOPS ===
        get("/api/stops", (req, res) -> {
            res.type("application/json");
            List<Document> results = new ArrayList<>();
            try (MongoCursor<Document> cursor = stops.find().limit(100).iterator()) {
                while (cursor.hasNext()) {
                    results.add(cursor.next());
                }
            }
            return gson.toJson(results);
        });
        
        // Søk i stops
        get("/api/stops/search/:query", (req, res) -> {
            res.type("application/json");
            String query = req.params("query");
            List<Document> results = new ArrayList<>();
            
            Document searchFilter = new Document("$or", Arrays.asList(
                new Document("stop_name", regex(query, "i")),
                new Document("stop_id", regex(query, "i")),
                new Document("city", regex(query, "i"))
            ));
            
            try (MongoCursor<Document> cursor = stops.find(searchFilter).limit(20).iterator()) {
                while (cursor.hasNext()) {
                    results.add(cursor.next());
                }
            }
            return gson.toJson(results);
        });
        
        // Hent journeys for et spesifikt stopp
        get("/api/journeys/stop/:stopId", (req, res) -> {
            res.type("application/json");
            String stopId = req.params("stopId");
            List<Map<String, Object>> results = new ArrayList<>();
            
            // Finn journeys_extra_info som inneholder dette stoppet
            try (MongoCursor<Document> cursor = journeysExtraInfo.find(
                eq("stops", stopId)).iterator()) {
                
                while (cursor.hasNext()) {
                    Document extraInfo = cursor.next();
                    String routeId = extraInfo.getString("routeId");
                    
                    // Finn tilsvarende journey
                    Document journey = journeys.find(eq("routeId", routeId)).first();
                    if (journey != null) {
                        Map<String, Object> journeyWithExtra = getJourneyWithExtraInfo(journey);
                        results.add(journeyWithExtra);
                    }
                }
            }
            return gson.toJson(results);
        });
        
        // Statistikk
        get("/api/stats", (req, res) -> {
            res.type("application/json");
            Map<String, Object> stats = new HashMap<>();
            
            stats.put("totalJourneys", journeys.countDocuments());
            stats.put("totalAgencies", agencies.countDocuments());
            stats.put("totalStops", stops.countDocuments());
            
            // Tell journeys per type
            stats.put("busJourneys", journeys.countDocuments(regex("routeId", "^BUS_")));
            stats.put("trainJourneys", journeys.countDocuments(regex("routeId", "^TRAIN_")));
            stats.put("boatJourneys", journeys.countDocuments(regex("routeId", "^BOAT_")));
            
            return gson.toJson(stats);
        });
        
        System.out.println("🚌 Transport System API kjører på http://localhost:8080");
        System.out.println("📊 Tilgjengelige endpoints:");
        System.out.println("  GET  /api/journeys              - Alle journeys med extra info");
        System.out.println("  GET  /api/journeys/type/:type   - Journeys etter type (BUS/TRAIN/BOAT)");
        System.out.println("  GET  /api/journeys/stop/:stopId - Journeys som stopper ved et sted");
        System.out.println("  GET  /api/agencies              - Alle transportører");
        System.out.println("  GET  /api/stops                 - Alle stoppesteder");
        System.out.println("  GET  /api/stops/search/:query   - Søk i stoppesteder");
        System.out.println("  GET  /api/stats                 - Statistikk");
    }
    
    private static Map<String, Object> getJourneyWithExtraInfo(Document journey) {
        Map<String, Object> result = new HashMap<>();
        result.put("journey", journey);
        
        String routeId = journey.getString("routeId");
        Document extraInfo = journeysExtraInfo.find(eq("routeId", routeId)).first();
        
        if (extraInfo != null) {
            result.put("extraInfo", extraInfo);
            
            // Hent agency info
            String agencyId = extraInfo.getString("agency_id");
            if (agencyId != null) {
                Document agency = agencies.find(eq("agency_id", agencyId)).first();
                result.put("agency", agency);
            }
            
            // Hent detaljer om stops
            List<String> stopIds = extraInfo.getList("stops", String.class);
            if (stopIds != null) {
                List<Document> stopDetails = new ArrayList<>();
                for (String stopId : stopIds) {
                    Document stop = stops.find(eq("stop_id", stopId)).first();
                    if (stop != null) {
                        stopDetails.add(stop);
                    }
                }
                result.put("stopDetails", stopDetails);
            }
        }
        
        return result;
    }
}