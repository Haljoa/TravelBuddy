package org.travel.Core.Ports;

import org.travel.Core.Domain.TripData;

import java.util.List;

public interface TripDataInputPort {
    //dette er da in-porten til tripdata delen av systemet
    //når noe skal ut av denne delen av systemet, sendes det gjennom out-porten,
    //men når noe skal inn, slik som å ta imot noe fra UI'et, skal det gjennom denne in-porten.

    void saveTripData(TripData tripData);
    TripData getRouteById(String routeId);
    List<TripData> getAllTrips();
}
