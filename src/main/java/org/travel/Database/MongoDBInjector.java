package main.java.org.travel.Database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBInjector {
    //denne klassen fungerer som en dependency injector for TripDataMongoAdapter.
    //her startes database tilkoblingen, og TripDataMongoAdapter sin konstruktør er satt opp slik at den
    //kan ta imot tilkoblingen.

    //connection stringen til databasen vår
    private static final String connection_string = "sett inn connection string til mongoDB her";
    //navnet på databasen dataen skal inn i
    private static final String database_name = "sett in navnet på databasen / dokumentet dataen skal inn i her";

    //kobler til databasen
    private static final MongoClient mongoClient = MongoClients.create(connection_string);

    //for å kunne injecte den i TripDataMongoAdapter
    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(database_name);
    }

    //koble fra DB
    public static void closeDB() {
        mongoClient.close();
    }
}
