package org.travel.Core.Domain;

import java.util.List;

public class TripPattern {
    private int duration;
    private double walkDistance;
    private List<Leg> legs;

    public TripPattern() {
    }

    public TripPattern(int duration, double walkDistance, List<Leg> legs) {
        this.duration = duration;
        this.walkDistance = walkDistance;
        this.legs = legs;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return "TripPattern {" +
                "duration = " + duration +
                "walkDistance = " + walkDistance +
                "legs = " + legs +
                "}";
    }
}