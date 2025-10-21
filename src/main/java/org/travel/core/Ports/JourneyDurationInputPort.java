package main.java.org.travel.core.Ports;

public interface JourneyDurationInputPort {
    //Input porten for JourneyDuration
    void recordJourneyDuration(int routeId, int from, int to, int durationInMinutes);
}
