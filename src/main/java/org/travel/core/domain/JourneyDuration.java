package main.java.org.travel.core.domain;

public class JourneyDuration {

    private int routeId;
    private int from;
    private int to;
    private int durationInMinutes;

    public JourneyDuration(int routeId, int from, int to, int durationInMinutes) {
        this.routeId = routeId;
        this.from = from;
        this.to = to;
        this.durationInMinutes = durationInMinutes;
    }

    public int getrouteId() {return routeId;}
    public int getFrom() {return from;}
    public int getTo() {return to;}
    public int getDurationInMinutes() {return durationInMinutes;}

    public void setDurationInMinutes(int durationInMinutes) {
        if (durationInMinutes <= 0) {
            throw new IllegalArgumentException("Venligst skriv inn et gyldig tall.");
        }
        this.durationInMinutes = durationInMinutes;
    }
}
