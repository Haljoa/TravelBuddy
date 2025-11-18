package org.travel.Core.Ports;

import org.travel.Core.Domain.TripData;

//dette er en out-port som brukes av Core for requests av TripData.
//den brukes av EnturTripDataAdapter.
public interface EnturTripDataPort {
    TripData fetchTripFromEntur(String from, String to);
}
