package main.java.org.travel.core.src.main.java.org.travel.service;

import main.java.org.travel.core.src.main.java.org.travel.domain.JourneyDuration;
import java.util.HashMap;

public class JourneyDurationService {
    private HashMap<Integer, JourneyDuration> journey = new HashMap();

    public void setJourneyDuration(int journeyId, int from, int to, int durationInMinutes) {
        JourneyDuration journeyDuration = journey.get(journeyId);

        journeyDuration.setDurationInMinutes(durationInMinutes);

        journey.put(journeyId, journeyDuration);
    }

    public JourneyDuration getJourney(int journeyId) {
        return journey.get(journeyId);
    }
}
