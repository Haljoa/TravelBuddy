package main.java.org.travel.Database;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBInjector {
    //denne klassen fungerer som en dependency injector for TripDataMongoAdapter.
    //her startes database tilkoblingen, og TripDataMongoAdapter sin konstruktør er satt opp slik at den
    //kan ta imot tilkoblingen.

    //connection stringen til databasen vår
    private static final String connection_string = "mongodb+srv://minhbaopokemon_db_user:pooplord123@cluster0.dikzcnh.mongodb.net/";
    //navnet på databasen dataen skal inn i
    private static final String database_name = "database1";

    //Måtte ha med POJO mapping for at tilkoblingen skulle fungere
    private static final CodecRegistry pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
    );
    //mongoClient settings med POJO registry
    private static final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new com.mongodb.ConnectionString(connection_string))
            .codecRegistry(pojoCodecRegistry)
            .build();

    //kobler til databasen
    private static final MongoClient mongoClient = MongoClients.create(settings);

    //for å kunne injecte den i TripDataMongoAdapter
    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(database_name);
    }

    //koble fra DB
    public static void closeDB() {
        mongoClient.close();
    }
}
