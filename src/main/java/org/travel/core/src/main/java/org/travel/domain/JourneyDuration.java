package org.travel.domain;

public class JourneyDuration {

    private int journeyId;
    private int from;
    private int to;
    private int durationInMinutes;

    public JourneyDuration(int journeyId, int from, int to, int durationInMinutes) {
        this.journeyId = journeyId;
        this.from = from;
        this.to = to;
        this.durationInMinutes = durationInMinutes;
    }

    public int getJourneyId() {return journeyId;}
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
