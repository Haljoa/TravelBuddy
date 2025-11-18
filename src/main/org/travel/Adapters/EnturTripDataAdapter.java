package org.travel.Adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.travel.API.GraphQLClient;
import org.travel.Core.Domain.EnturTrip;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Ports.EnturTripDataPort;
import org.travel.Core.Ports.JourneyClientPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EnturTripDataAdapter implements EnturTripDataPort {
    //denne klassen står for kommunikasjonen mellom systemet og det eksterne EnTur systemet
    //den kaller GraphQLClient gjennom porten dens (JourneyClientPort), får tak i ustrukturert
    //json fra apiet, og deretter transformerer dette til et TripData objekt som vi kan bruke

    //injekterer GraphQLClient
    private final JourneyClientPort clientPort;
    private final ObjectMapper mapper = new ObjectMapper();

    public EnturTripDataAdapter(JourneyClientPort clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public TripData fetchTripFromEntur(String from, String to) {
        try {
            //henter dataen fra entur, uten formatering.
            String rawJson = clientPort.journeyV3Query(from, to);

            //formaterer dataen på ønsket måte, og mapper den til EnturTrip, nestet i TripData.
            JsonNode root = mapper.readTree(rawJson);
            JsonNode tripNode = root.path("data").path("trip");
            EnturTrip enturTrip = mapper.treeToValue(tripNode, EnturTrip.class);

            TripData tripData = new TripData(
                    from + " - " + to,
                    0,
                    0,
                    "none",
                    enturTrip
            );

            return tripData;

        } catch (Exception exception) {
            throw new RuntimeException("Entur API failed.", exception);
        }
    }
}
