package org.travel.Core.Domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collections;
=======
>>>>>>> Brukere
import java.util.List;

public class Journeymaker {
    private final ObjectMapper mapper;
    private final JsonNode root;

    public Journeymaker(String jsonString) throws IOException {
        this.mapper = new ObjectMapper();
        this.root = mapper.readTree(jsonString);
    }

    /**
     * Extracts each tripPattern from the response as a separate JSON string segment.
     * Each segment will start with the "duration" field and include all its nested data.
     */
<<<<<<< HEAD

    // Method to extract trip pattern segments
    public List<JsonNode> splitTripSegments() {
        List<JsonNode> segments = new ArrayList<>();

        // Navigate safely through the JSON tree
        JsonNode tripPatterns = root
                .path("data")
                .path("trip")
                .path("tripPatterns");

        if (tripPatterns.isArray()) {
            for (JsonNode pattern : tripPatterns) {
                segments.add(pattern);
=======
    public List<String> splitTripSegments() {
        JsonNode tripPatterns = root.path("data").path("trip").path("tripPatterns");

//        if (!tripPatterns.isArray() || tripPatterns.isMissingNode()) {
//            return Collections.emptyList();
//        }

        List<String> segments = new ArrayList<>();

        for (JsonNode pattern : tripPatterns) {
            // Each pattern corresponds to one "segment"
            // Convert it back to a JSON string
            try {
                String segmentJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pattern);
                segments.add(segmentJson);
            } catch (Exception InvalidResponse) {
                throw new RuntimeException("Failed to convert trip pattern to string", InvalidResponse);
>>>>>>> Brukere
            }
        }

        return segments;
    }
}
<<<<<<< HEAD

=======
>>>>>>> Brukere
