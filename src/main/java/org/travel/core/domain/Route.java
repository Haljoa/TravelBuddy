package main.java.org.travel.core.domain;

import java.util.List;

public class Route {
    private int routeId;
    private List<Integer> stops;

    public Route() {}

    public Route(int routeId, List<Integer> stops) {
        this.routeId = routeId;
        this.stops = stops;
    }

    public int getRouteId() {return routeId;}
    public void setRouteId(int routeId) {this.routeId = routeId;}
    public List<Integer> getStops() {return stops;}
    public void setStops(List<Integer> stops) {this.stops = stops;}

    @Override
    public String toString() {
        return "Route{" +
                "routeId: " + routeId +
                "stops: " + stops +
                "}";
    }
}
