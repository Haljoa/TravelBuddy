package main.java.org.travel.core.src.main.java.org.travel.domain;

import java.util.List;

public class TripData {
    private int routeId;
    private int totalRouteDuration;
    private List<Integer> durationsBetweenStops;
    private int crowdednessLevel; //
    private String deviations;

    //Jackson trenger en tom konstruktør
    public TripData() {}

    public TripData(int routeId, int totalRouteDuration, List<Integer> durationsBetweenStops,
                    int crowdednessLevel, String deviations) {
        this.routeId = routeId;
        this.totalRouteDuration = totalRouteDuration;
        this.durationsBetweenStops = durationsBetweenStops;
        this.crowdednessLevel = crowdednessLevel;
        this.deviations = deviations;
    }

    public int getRouteId() {return routeId;}
    public int getTotalRouteDuration() {return totalRouteDuration;}
    public List<Integer> getDurationsBetweenStops() {return durationsBetweenStops;}
    public int getCrowdednessLevel() {return crowdednessLevel;}
    public String getDeviations() {return deviations;}

    public void setRouteId(int routeId) {this.routeId = routeId;}
    public void setTotalRouteDuration(int totalRouteDuration) {this.totalRouteDuration = totalRouteDuration;}
    public void setDurationsBetweenStops(List<Integer> durationsBetweenStops) {this.durationsBetweenStops = durationsBetweenStops;}
    public void setCrowdednessLevel(int crowdednessLevel) {this.crowdednessLevel = crowdednessLevel;}
    public void setDeviations(String deviations) {this.deviations = deviations;}
}