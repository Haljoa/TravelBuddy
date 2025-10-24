package main.java.org.travel;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.java.org.travel.Adapters.TripDataMongoAdapter;
import main.java.org.travel.Database.MongoDBInjector;
import main.java.org.travel.core.Ports.TripDataRepository;
import main.java.org.travel.core.domain.TripData;
import main.java.org.travel.core.service.TripDataService;
import org.bson.Document;

import java.util.List;

public class DatabaseConnectionTester {
    public static void main(String[] args) {
        //bruker dependency injectoren til å sette opp tilkoblingen til databasen,
        //og lar oss bruke mongoDB funksjoner mot databasen selv
        MongoDBInjector injector = new MongoDBInjector();

        //oppretter adapteren og kobler den til interfacet, eller motsatt da.
       //vi gir adapteren databasen direkte fra injectoren, noe adapteren er laget til å ta imot
        //og den har da den rette collectionen allerede i seg.
        TripDataRepository repository = new TripDataMongoAdapter(injector.getDatabase());

        //oppretter service klassen for å gjøre operasjoner.
        TripDataService service = new TripDataService(repository);

        service.getAllTrips();

        //feilsøking.
        MongoDatabase database = injector.getDatabase();
        System.out.println("Database name: " + database.getName());
        //ser ut som dette var feilen. jeg bare leste fra feil database :)

        //denne delen må skrives om når den andre delen av systemet som tar imot EnTur data er ferdig og
        //skal sendes med her istedet.
        //kanskje lage en metode som lager en ruteId for den dataen som hentes, og returnerer den slik at den
        //kan settes inn her
        TripData trip = new TripData("Route_5", 15, List.of(1,4,5,2), 5, "Some traffic");
        service.saveTripData(trip);

        //lagre bare en deviation f.eks.
        String routeId = "Route_2";
        TripData myTrip = service.getRouteById(routeId);

        if (myTrip == null) {
            System.out.println("Cant find this routeId.");
            return;
        }

        myTrip.setDeviations("Traffic standing still.");
        service.saveTripData(myTrip);

        //DET FUNKER <|:^)))))))))))))
        //nevermind :(

        injector.closeDB();
    }
}
