package main.java.org.travel.Adapters;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.java.org.travel.core.domain.TripData;

public class TripDataMongoAdapter {
    //denne klassen/adapteren står for kommunikasjonen mellom core og databasen vår.
    //den lagrer, oppdaterer, og leser TripData objekter fra og til databasen.

    //for CRUD operasjoner med TripData objekter
    private final MongoCollection<TripData> collection;

    //konstruktøren som tar imot DI-en fra MongoDBInjector
    public TripDataMongoAdapter (MongoDatabase database) {
        //her finner mongoDB rett collection med data, og Java driver får klassen som den skal lage objekter av.
        this.collection = database.getCollection("sett inn navnet på rett collection her", TripData.class);
    }

    //CRUD operasjoner under her
}
