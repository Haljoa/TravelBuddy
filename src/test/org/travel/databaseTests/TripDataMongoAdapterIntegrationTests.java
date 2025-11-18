package org.travel.databaseTests;

import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.configuration.IMockitoConfiguration;
import org.travel.Adapters.TripDataMongoAdapter;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Ports.TripDataRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripDataMongoAdapterIntegrationTests {
    //henter inn in-memory test serveren til å bruke for disse testene
    private static MongoDBInMemoryTestServer mongoServer;
    private static TripDataMongoAdapter adapter;

    @BeforeAll
    static void setup() throws Exception {
        //starter in-memory test serveren for alle disse testene, unødvendig å gjøre det før hver av dem.
        mongoServer = new MongoDBInMemoryTestServer();
        MongoDatabase db = mongoServer.start();
        //kobler adapteren til test serveren
        adapter = new TripDataMongoAdapter(db);
    }

    @AfterAll
    static void cleanup() {
        //stopper test serveren etter testene er ferdige
        mongoServer.stop();
    }

    @Test
    void getAllTripsShouldReturnListContainingAllTrips() {
        //arrange
        adapter.saveTripData(new TripData("a - b", 10, 3, "Loud people bothering others", null));
        adapter.saveTripData(new TripData("c - d", 15, 7, "traffic", null));

        //act
        List<TripData> trips = adapter.getAllTrips();

        //assert
        //sjekker at i hvertfall to turer er i listen
        assertTrue(trips.size() >= 2);
    }

    @Test
    void saveAndGetTripShouldReturnTheSameData() {
        //arrange
        TripData trip = new TripData("e - f", 55, 6, "none", null);

        //act
        //lagrer dataen til db, for så å hente ut samme data
        adapter.saveTripData(trip);
        TripData result = adapter.getRouteById("e - f");

        //assert
        //finnes dette objektet og er dataen lik?
        assertNotNull(result);
        assertEquals("e - f", result.getRouteId());
        assertEquals(55, result.getTotalRouteDuration());
        assertEquals(6, result.getCrowdednessLevel());
        assertEquals("none", result.getDeviations());
    }

    //sjekker at getRouteById returnerer null, hvis en tur ikke eksisterer, og ikke noe annet tilfeldig
    @Test
    void getRouteByIdShouldReturnNullIfTripDoesNotExist() throws Exception {
        //arrange
        //lager en ruteId som ikke finnes, og hopefully ikke kommer til å finnes :)
        String fakeTripId = "where did you even find me?";

        //act
        TripData result = adapter.getRouteById(fakeTripId);

        //assert
        assertNull(result);
    }
}
