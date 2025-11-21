package org.travel.serviceTests;

import org.junit.jupiter.api.Test;
import org.travel.Core.Domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HumanReadableTripFormatterTests {

    //her sjekker vi at toHumanReadable metoden formaterer dataen den tar imot på ønsket måte
    @Test
    void toHumanReadableFormatsTripCorrectlyWithGivenData() {
        //arrange
        //setter opp en mock trip med data formatert på samme måte som entur sender den
        Leg leg = new Leg();
        leg.setMode("bus");
        leg.setDistance(1000);
        leg.setExpectedStartTime("2025-11-20T01:00:00+01:00");
        leg.setExpectedEndTime("2025-11-20T01:10:00+01:00");

        TripPattern pattern = new TripPattern();
        pattern.setDuration(600);
        pattern.setWalkDistance(200);
        pattern.setLegs(List.of(leg));

        EnturTrip enturTrip = new EnturTrip();
        enturTrip.setTripPatterns(List.of(pattern));

        TripData trip = new TripData();
        trip.setEnturTrip(enturTrip);
        trip.setCrowdednessLevel(5);
        trip.setTotalRouteDuration(10);
        trip.setDeviations("none");

        //act
        List<String> result = HumanRedableTripFormatter.toHumanReadable(trip);

        //assert
        //sjekker at formatereren har omgjort daten vi sendte til den, til det menneske-vennlige formatet det skal.
        //altså at det vi får tilbake inneholder de tekst strengene som den skal legge til.
        assertTrue(result.stream().anyMatch(s -> s.contains("Actual trip duration: 10")));
        assertTrue(result.stream().anyMatch(s -> s.contains("Take bus")));
        assertTrue(result.stream().anyMatch(s -> s.contains("Option 1")));
    }

    @Test
    void toHumanReadableReturnsMessageWhenReceivedDataIsNull() {
        //arrange og act
        List<String> result = HumanRedableTripFormatter.toHumanReadable(null);
        //assert
        assertEquals(1, result.size());
        assertEquals("There are currently no trips available.", result.get(0));
    }
}
