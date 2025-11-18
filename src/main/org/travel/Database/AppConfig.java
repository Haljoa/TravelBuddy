package org.travel.Database;

import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.travel.API.GraphQLClient;
import org.travel.Adapters.EnturTripDataAdapter;
import org.travel.Adapters.TripDataMongoAdapter;
import org.travel.Core.Ports.EnturTripDataPort;
import org.travel.Core.Ports.TripDataRepository;
import org.travel.Core.Service.TripDataService;

//dette er en konfigurerings-klasse som står for sammenkoblingen av ulike dependencies som må skje når appen starter
//den gjør at Core kun kobles til porter, og ikke noe annet.
//jeg tror Spring kan gjøre dette automatisk, men hvis vi gjør det slik er vi sikre på at alt kobles sammen på rett måte
//spesielt med tanke på å opprettholde den heksagonale arkitekturen.
@Configuration
public class AppConfig {

    @Bean
    public MongoDatabase mongoDatabase() {
        return new MongoDBInjector().getDatabase();
    }

    @Bean
    public TripDataRepository tripDataRepository(MongoDatabase db) {
        return new TripDataMongoAdapter(db);
    }

    @Bean
    public EnturTripDataPort enturPort() {
        return new EnturTripDataAdapter(new GraphQLClient());
    }

    @Bean
    public TripDataService tripDataService(TripDataRepository repo, EnturTripDataPort enturPort) {
        return new TripDataService(repo, enturPort);
    }
}
