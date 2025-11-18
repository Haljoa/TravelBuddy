package org.travel.Core.Domain;

public class Leg {
    private String expectedStartTime;
    private String expectedEndTime;
    private String mode;
    private double distance;
    private Line line;

    public Leg() {
    }

    public Leg(String expectedStartTime, String expectedEndTime, String mode, double distance, Line line) {
        this.expectedStartTime = expectedStartTime;
        this.expectedEndTime = expectedEndTime;
        this.mode = mode;
        this.distance = distance;
        this.line = line;
    }

    public String getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(String expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public String getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(String expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Leg {" +
                "expectedStartTime = " + expectedStartTime +
                "expectedEndTime = " + expectedEndTime +
                "mode = " + mode +
                "distance = " + distance +
                "line = " + line +
                "}";
    }
}