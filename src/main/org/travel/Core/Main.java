package org.travel.Core;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.JsonNode;
import org.travel.API.GraphQLClient;
import org.travel.Core.Domain.Journeymaker;

import java.util.List;

public class Main {

public static void main(String[] args) throws Exception {
    Journeymaker jm = new Journeymaker(GraphQLClient.JourneyV3Query("NSR:StopPlace:2534", "NSR:StopPlace:60053"));
    List<JsonNode> patterns = jm.splitTripSegments();

    System.out.println("Trip Patterns:");
    for (JsonNode pattern : patterns) {
        // Pretty-print each JSON object
        System.out.println(pattern.toPrettyString());

    }
}
    }

=======
public class Main {
    public static void main(String[] args) {

    }
}
>>>>>>> Brukere
