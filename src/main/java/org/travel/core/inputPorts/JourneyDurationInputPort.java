package main.java.org.travel.core.inputPorts;

public interface JourneyDurationInputPort {
    //Input porten for JourneyDuration
    void recordJourneyDuration(int routeId, int from, int to, int durationInMinutes);
}
