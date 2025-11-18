package org.travel.databaseTests;

import org.junit.jupiter.api.Test;
import org.travel.API.GraphQLClient;
import org.travel.Adapters.EnturTripDataAdapter;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Ports.JourneyClientPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnturTripDataAdapterTests {

    @Test
    void fetchingTripFromEnturShouldReturnJsonWhichIsThenParsedToTripDataFormatting() throws Exception {
        //arrange
        //lager en mock av GraphQLClient sin port, slik at vi kan få en respons uten å faktisk kalle apiet
        JourneyClientPort client = mock(JourneyClientPort.class);
        //plugger den inn i adapteren
        EnturTripDataAdapter adapter = new EnturTripDataAdapter(client);
        //lager en json "respons" med samme format som entur gir
        String jsonResponse = """
                {"data": {"trip": {"tripPatterns": []}}}
                """;
        //stubber respons
        when(client.journeyV3Query("a", "b")).thenReturn(jsonResponse);

        //act
        TripData result = adapter.fetchTripFromEntur("a", "b");

        //assert
        //sjekker at ruteIDen ble laget korrekt, og at vi fikk laget et objekt utav responsen
        assertEquals("a - b", result.getRouteId());
        assertNotNull(result.getEnturTrip());
    }

    @Test
    void fetchTripShouldThrowRuntimeExceptionOnInvalidJson() throws Exception {
        //arrange
        //mocker apiet
        JourneyClientPort client = mock(JourneyClientPort.class);
        EnturTripDataAdapter adapter = new EnturTripDataAdapter(client);
        //stubber ukorrekt json respons
        when(client.journeyV3Query("a", "b")).thenReturn("halla sjef");

        //act og assert
        //konverteringen bør gå galt og kaste en runtime exception
        assertThrows(RuntimeException.class, () -> adapter.fetchTripFromEntur("a", "b"));
    }
}
