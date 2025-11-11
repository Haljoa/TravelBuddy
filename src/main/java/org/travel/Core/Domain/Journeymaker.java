package org.travel.Core.Domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
            }
        }

        return segments;
    }
}

