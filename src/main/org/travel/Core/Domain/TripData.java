package org.travel.Core.Domain;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class TripData {
    //routeId må ha BsonId og BsonProperty knyttet til den, siden dette brukes som en unik id i databasen
    @BsonId
    @BsonProperty("_id")
    private String routeId;

    private int totalRouteDuration;
    private double crowdednessLevel; //
    private String deviations;

    //her er EnTur dataen nestet i TripData
    private EnturTrip enturTrip;

    //Jackson trenger en tom konstruktør
    public TripData() {
    }

    public TripData(String routeId, int totalRouteDuration, double crowdednessLevel, String deviations,
                    EnturTrip enturTrip) {
        this.routeId = routeId;
        this.totalRouteDuration = totalRouteDuration;
        this.crowdednessLevel = crowdednessLevel;
        this.deviations = deviations;
        this.enturTrip = enturTrip;
    }

    public String getRouteId() {
        return routeId;
    }

    public int getTotalRouteDuration() {
        return totalRouteDuration;
    }

    public double getCrowdednessLevel() {
        return crowdednessLevel;
    }

    public String getDeviations() {
        return deviations;
    }

    public EnturTrip getEnturTrip() {
        return enturTrip;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setTotalRouteDuration(int totalRouteDuration) {
        this.totalRouteDuration = totalRouteDuration;
    }

    public void setCrowdednessLevel(double crowdednessLevel) {
        this.crowdednessLevel = crowdednessLevel;
    }

    public void setDeviations(String deviations) {
        this.deviations = deviations;
    }

    public void setEnturTrip(EnturTrip enturTrip) {
        this.enturTrip = enturTrip;
    }

    @Override
    public String toString() {
        return "TripData{" +
                "routeId = " + routeId + "\n" +
                "totalRouteDuration = " + totalRouteDuration + "\n" +
                "crowdednessLevel = " + crowdednessLevel + "\n" +
                "deviations = " + deviations + "\n" +
                "enturTrip = " + enturTrip +
                "}";
    }
}