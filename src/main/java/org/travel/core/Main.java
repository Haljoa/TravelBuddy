package main.java.org.travel.core;

import main.java.org.travel.core.domain.Route;
import main.java.org.travel.core.domain.TripData;
import main.java.org.travel.core.service.RouteService;
import main.java.org.travel.core.service.TripDataService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // "Testing" av RouteService og RouteSearcher.
        RouteService routeService = new RouteService();
        List<Route> routes = routeService.loadRoutesFromJSON();

        System.out.println("\nLoaded routes from JSON. " + routes.size() + " routes loaded.");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your boarding stop nr: ");
        int boardingStop = scanner.nextInt();

        System.out.println("Enter your departure stop nr: ");
        int departureStop = scanner.nextInt();

        List<Route> routeList = routeService.findRoutesWithRouteSearcher(routes, boardingStop, departureStop);

        if (routeList.isEmpty()) {
            System.out.println("No route contains both these stops.");
        } else {
            System.out.println(routeList.size() + " routes were found: \n");
            for (Route route : routeList) {
                System.out.println("Route " + route.getRouteId() + ". With the stops " + route.getStops());
            }
        }

        //Test av TripData og TripDataService
        TripDataService tripDataService = new TripDataService();

        TripData trip1 = new TripData(5, 25, List.of(4, 3, 7, 4, 2), 4, "Some traffic");
        TripData trip2 = new TripData(12, 16, List.of(6, 4, 15, 6, 7, 7, 13), 2, "Drunk guy yelling at people.");
        tripDataService.saveTripData(trip1);
        tripDataService.saveTripData(trip2);
    }
}
