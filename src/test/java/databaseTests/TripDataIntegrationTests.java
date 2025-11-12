package test.java.databaseTests;

import com.mongodb.client.MongoDatabase;
import org.travel.Adapters.TripDataMongoAdapter;
import org.travel.Core.Ports.TripDataInputPort;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Service.TripDataService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TripDataIntegrationTests {
    //bruker test-serveren
    private static MongoDBInMemoryTestServer server;
    //bruker in-porten som inngangs punkt
    private static TripDataInputPort service;

    @BeforeAll
    static void setup() throws Exception {
        //starter in-memory serveren
        server = new MongoDBInMemoryTestServer();
        MongoDatabase database = server.start();
        //kobler adapteren til in-memory databasen
        TripDataMongoAdapter adapter =  new TripDataMongoAdapter(database);
        //kobler sammen adapteren til service klassen via in-porten
        service = new TripDataService(adapter);
        //test koden her vil da bevege seg til service klassen via in-porten,
        //deretter til adapteren gjennom out-porten, og til in-memory test serveren.
    }

    @AfterAll
    static void teardown() {
        //stenger databasen etter testene
        server.stop();
    }

    @Test
    void testingSaveAndGetTrips() {
        //arrange
        TripData trip = new TripData(
                "Route1", 25, Arrays.asList(2, 12, 5, 6), 0.8, "Some Traffic"
        );

        //act
        //lagrer denne til in-memory databasen via in-porten og service
        service.saveTripData(trip);
        //henter denne fra DB basert på IDen
        TripData gotById = service.getRouteById("Route1");

        //assert
        Assertions.assertNotNull(gotById);
        Assertions.assertEquals("Route1", gotById.getRouteId());
        Assertions.assertEquals(25, gotById.getTotalRouteDuration());
        Assertions.assertEquals(List.of(2, 12, 5, 6), gotById.getDurationsBetweenStops());
        Assertions.assertEquals(0.8, gotById.getCrowdednessLevel());
        Assertions.assertEquals("Some Traffic", gotById.getDeviations());
    }

    @Test
    void testingGetAllTrips() {
        //arrange
        TripData trip1 = new TripData(
                "Route2", 20, List.of(2, 5, 5, 12, 4), 0.3,
                "Loud man shouting at people");
        TripData trip2 = new TripData(
                "Route3", 39, List.of(12, 2, 4, 15, 6), 0.6,
                "Driver playing loud music and singing");

        service.saveTripData(trip1);
        service.saveTripData(trip2);

        //act
        List<TripData> allTrips = service.getAllTrips();

        //assert
        Assertions.assertEquals(3, allTrips.size());
        //ser om rutene faktisk ligger i listen
        Assertions.assertTrue(allTrips.stream().anyMatch(t -> t.getRouteId().equals("Route2")));
        Assertions.assertTrue(allTrips.stream().anyMatch(t -> t.getRouteId().equals("Route3")));
    }
}
