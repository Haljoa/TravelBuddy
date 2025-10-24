package main.java.org.travel.Adapters;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import main.java.org.travel.core.Ports.TripDataRepository;
import main.java.org.travel.core.domain.TripData;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class TripDataMongoAdapter implements TripDataRepository {
    //denne klassen/adapteren står for kommunikasjonen mellom core og databasen vår.
    //den lagrer, oppdaterer, og leser TripData objekter fra og til databasen.

    //for CRUD operasjoner med TripData objekter
    private final MongoCollection<TripData> collection;

    //konstruktøren som tar imot DI-en fra MongoDBInjector
    public TripDataMongoAdapter (MongoDatabase database) {
        //her finner mongoDB rett collection med data, og Java driver får klassen som den skal lage objekter av.
        this.collection = database.getCollection("journeysTest", TripData.class);
    }

    //CRUD operasjoner under her
    //alt dette er funnet på MongoDB Java Driver sine offisielle docs sider under CRUD Operations:
    //https://www.mongodb.com/docs/drivers/java/sync/current/
    @Override
    public void saveTripData(TripData tripData) {
        //bruker et filter til å finne samme routeId i databasen som vi sender med som et TripData objekt
        Bson idMatch = Filters.eq("routeId", tripData.getRouteId());
        //bytter ut gammel info om ruten ved å overskrive den med ny data, eller lagre ny data hvis ingen finnes
        try {
            collection.replaceOne(idMatch, tripData, new ReplaceOptions().upsert(true));
            System.out.println("Route has been updated.");
        } catch (MongoException exception) {//MongoException tar hånd om alle feil relatert til databasen
            System.err.println("Something went wrong. " + exception.getMessage());
        }
    }

    @Override
    public TripData findRouteById(String routeId) {
        try {
            //finner det første dokumentet i DB hvor PK er lik ruteIDen vi sender med.
            TripData route = collection.find(Filters.eq("routeId", routeId)).first();
            return route;
        } catch (MongoException exception) {
            System.err.println("Something went wrong. " + exception.getMessage());
            return null;
        }
    }

    @Override
    public List<TripData> findAllTrips() {
        //finner alle dokumenter i database collectionen som er definert i konstruktøren i denne klassen,
        //og legger dem inn i en liste.
        List<TripData> allTrips = new ArrayList<>();
        try {
            collection.find().into(allTrips);
            System.out.println("Fetched " + allTrips.size() + " trips.");
        } catch (MongoException exception) {
            System.err.println("Something went wrong. " + exception.getMessage());
        }
        return allTrips;
    }
}
