package main.java.org.travel.core.src.main.java.org.travel.domain;

import java.util.ArrayList;
import java.util.List;

public class RouteSearcher {

    public List<Route> routesWithTheseSpecificStops(List<Route> routes, int boardingStop, int departureStop) {
        List<Route> foundRoutes = new ArrayList<>();
        for (Route route : routes) {
            List<Integer> stops = route.getStops();
            if (stops.contains(boardingStop) && stops.contains(departureStop)) {
                foundRoutes.add(route);
            }
        }
        return foundRoutes;
    }
}
