package main.java.org.travel.Core.Ports;

public interface JourneyDurationInputPort {
    //Input porten for JourneyDuration
    void recordJourneyDuration(int routeId, int from, int to, int durationInMinutes);
}
