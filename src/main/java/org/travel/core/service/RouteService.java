package main.java.org.travel.core.src.main.java.org.travel.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.org.travel.core.src.main.java.org.travel.domain.Route;
import main.java.org.travel.core.src.main.java.org.travel.domain.RouteSearcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteService {
    //laste routes fra JSON, og bruke routesearcher for å finne ønskede ruter

    private static final String FILE_PATH = "dummyRouteData.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RouteSearcher routeSearcher = new RouteSearcher();

    public List<Route> loadRoutesFromJSON() {
        try {
            File file = new File(FILE_PATH);
            return objectMapper.readValue(file, new TypeReference<List<Route>>() {});
        } catch (IOException exception) {
            System.err.println("Something went wrong " + exception.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Route> findRoutesWithRouteSearcher(List<Route> routes, int boardingStop, int departureStop) {
        return routeSearcher.routesWithTheseSpecificStops(routes, boardingStop, departureStop);
    }
}
