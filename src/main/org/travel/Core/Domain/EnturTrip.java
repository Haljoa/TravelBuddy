package org.travel.Core.Domain;

import java.util.List;

public class EnturTrip {
    private List<TripPattern> tripPatterns;

    public EnturTrip() {
    }

    public EnturTrip(List<TripPattern> tripPatterns) {
        this.tripPatterns = tripPatterns;
    }

    public List<TripPattern> getTripPatterns() {
        return tripPatterns;
    }

    public void setTripPatterns(List<TripPattern> tripPatterns) {
        this.tripPatterns = tripPatterns;
    }

    @Override
    public String toString() {
        return "EnturTrip {" +
                "tripPatterns = " + tripPatterns +
                "}";
    }
}