package main.java.org.travel.core.src.main.java.org.travel;

import main.java.org.travel.core.src.main.java.org.travel.domain.Route;
import main.java.org.travel.core.src.main.java.org.travel.service.RouteService;

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
    }
}
