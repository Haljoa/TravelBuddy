package main.java.org.travel.core.domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class TripData {
    //routeId må ha BsonId og BsonProperty knyttet til den, siden dette brukes som en unik id i databasen
    @BsonId
    @BsonProperty("_id")
    private String routeId;

    private int totalRouteDuration;
    private List<Integer> durationsBetweenStops;
    private double crowdednessLevel; //
    private String deviations;

    //Jackson trenger en tom konstruktør
    public TripData() {}

    public TripData(String routeId, int totalRouteDuration, List<Integer> durationsBetweenStops,
                    double crowdednessLevel, String deviations) {
        this.routeId = routeId;
        this.totalRouteDuration = totalRouteDuration;
        this.durationsBetweenStops = durationsBetweenStops;
        this.crowdednessLevel = crowdednessLevel;
        this.deviations = deviations;
    }

    public String getRouteId() {return routeId;}
    public int getTotalRouteDuration() {return totalRouteDuration;}
    public List<Integer> getDurationsBetweenStops() {return durationsBetweenStops;}
    public double getCrowdednessLevel() {return crowdednessLevel;}
    public String getDeviations() {return deviations;}

    public void setRouteId(String routeId) {this.routeId = routeId;}
    public void setTotalRouteDuration(int totalRouteDuration) {this.totalRouteDuration = totalRouteDuration;}
    public void setDurationsBetweenStops(List<Integer> durationsBetweenStops) {this.durationsBetweenStops = durationsBetweenStops;}
    public void setCrowdednessLevel(double crowdednessLevel) {this.crowdednessLevel = crowdednessLevel;}
    public void setDeviations(String deviations) {this.deviations = deviations;}
}