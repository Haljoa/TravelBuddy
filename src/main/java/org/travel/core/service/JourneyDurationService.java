package main.java.org.travel.core.service;

import main.java.org.travel.core.domain.JourneyDuration;
import java.util.HashMap;

public class JourneyDurationService {
    private HashMap<Integer, JourneyDuration> journey = new HashMap();

    public void setJourneyDuration(int routeId, int from, int to, int durationInMinutes) {
        JourneyDuration journeyDuration = journey.get(routeId);

        journeyDuration.setDurationInMinutes(durationInMinutes);

        journey.put(routeId, journeyDuration);
    }

    public JourneyDuration getJourney(int routeId) {
        return journey.get(routeId);
    }
}
