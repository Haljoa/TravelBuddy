package main.java.org.travel.core.src.main.java.org.travel.inputPorts;

public interface JourneyDurationInputPort {
    //Input porten for JourneyDuration
    void recordJourneyDuration(int journeyId, int from, int to, int durationInMinutes);
}
