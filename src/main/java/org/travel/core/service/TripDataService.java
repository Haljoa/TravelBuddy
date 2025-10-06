package main.java.org.travel.core.src.main.java.org.travel.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.org.travel.core.src.main.java.org.travel.domain.TripData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//lese og skrive til og fra JSON -> tar input fra TripData klassen og lager JSON objekter av dataen vi sender herfra

public class TripDataService {
    private static final String file_path = "journeys.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveTripData(TripData tripData) {
        List<TripData> journeys = readJourneys();
        journeys.add(tripData);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(file_path), journeys);
            System.out.println("Route " + tripData.getRouteId() + " has been saved");
        } catch (IOException exception) {
            System.err.println("Something went wrong: " + exception.getMessage());
        }
    }

    public List<TripData> readJourneys() {
        try {
            File file = new File(file_path);
            return objectMapper.readValue(file, new TypeReference<List<TripData>>() {});
        } catch (IOException exception) {
            System.err.println("Something went wrong reading the data: " + exception.getMessage());
            return new ArrayList<>();
        }
    }
}
