package main.java.org.travel.Core.Ports;

import main.java.org.travel.Core.Domain.TripData;

import java.util.List;

public interface TripDataRepository {
    //slik jeg forstod det, kan porter sees på som stikkontakter på utsiden av kjernen i arkitekturen.
    //stikkontakten bestemmer hvordan noe som plugges inn i den må se ut, men ikke nødvendigvis hvordan
    //det må fungere eller oppføre seg.
    //denne porten definerer da hvordan adapteren TripDataMongoAdapter, som "kobles til" her for å skape en kobling
    //mellom databasen vår og kjernen, må se ut for at tilkoblingen skal fungere.
    //dette blir CRUD operasjoner til og fra databasen.

    //for lagring av ekstra data om ruten
    void saveTripData(TripData tripData);
    //for å hente data om én rute basert på Iden dens
    TripData findRouteById(String routeId);
    //henting av all data om alle ruter
    List<TripData> findAllTrips();
}
