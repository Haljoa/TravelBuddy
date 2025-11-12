package org.travel.databaseTests;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBInMemoryTestServer {
    //for å starte og stoppe in-memory serveren med flapdoodle
    private MongodExecutable executable;
    private MongoClient client;

    //her startes in-memory test serveren
    public MongoDatabase start() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();

        //konfigurere in-memory serveren med versjon av mongodb og hvilken nettverks-port den skal bruke
        MongodConfig config = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27017, false))//tydeligvis en standard port som flapdoodle bruker
                .build();

        //starting av databasen. sender først med konfigurasjonen rett ovenfra.
        executable = starter.prepare(config);
        executable.start();

        //konvertering av POJO (TripData objekter) til BSON (mongodb sitt JSON format) og tilbake
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        //instillinger for å koble MongoClient til in-memory databasen
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(java.util.Collections.singletonList(
                                new com.mongodb.ServerAddress("localhost", 27017)
                        ))).codecRegistry(codecRegistry).build();

        //kobler til in-memory serveren
        client = MongoClients.create(settings);
        //returnerer databasen
        return client.getDatabase("testDB");
    }

    //for å stoppe in-memory databasen
    public void stop() {
        client.close();
        executable.stop();
    }
}
