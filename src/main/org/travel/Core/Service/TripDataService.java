package org.travel.Core.Service;

import org.travel.Core.Domain.TripPattern;
import org.travel.Core.Ports.EnturTripDataPort;
import org.travel.Core.Ports.TripDataRepository;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Ports.TripDataInputPort;

import java.util.List;

//Denne klassen er service klassen for hele TripData "familien", og til databasen vår. av alle klassene i denne kjeden,
// fra TripData (som er hva vi her ønsker å lage objekter av, og å kunne sende til, og hente fra databasen)
// til databasen selv, er det denne klassen man bruker når man ønsker å gjøre CRUD operasjoner.
//man kaller ikke f.eks. en adapter, her TripDataMongoAdapter, eller en port, her TripDataRepository, direkte for
//dette, man gjør det gjennom en service klasse som denne, som deretter sender instruksjonene videre.

//Kjeden går slik:
//UI ->
//Kontroller (TripDataController) ->
//in-port (TripDataInputPort) ->
//Service (TripDataService) ->
//out-ports (TripDataRepository og EnturTripDataPort) ->
//adaptere (TripDataMongoAdapter og EnturTripDataAdapter) ->
//eksterne tjenester (MongoDB og GraphQL/Entur API via GraphQLClient)

public class TripDataService implements TripDataInputPort {
    //for å snakke med porten / interfacet
    private final TripDataRepository repository;
    private final EnturTripDataPort enturPort;

    public TripDataService(TripDataRepository repository, EnturTripDataPort enturPort) {
        this.repository = repository;
        this.enturPort = enturPort;
    }

    //validering av bruker input
    private void validateUserInput(String from, String to) {
        if (from == null || to == null || from.isBlank() || to.isBlank()) {
            throw new IllegalArgumentException("From or To cannot be null or empty.");
        }
    }

    //for lagring av TripData til repository (sende det dertil)
    @Override
    public void saveTripData(TripData tripData) {
        repository.saveTripData(tripData);
    }

    //for henting av info om en rute via ID
    @Override
    public TripData getRouteById(String routeId) {
        return repository.getRouteById(routeId);
    }

    //hente alle ruter og info
    @Override
    public List<TripData> getAllTrips() {
        return repository.getAllTrips();
    }

    //henter reisen fra entur hvis den ikke allerede er i databasen vår
    public TripData getOrFetchTrip(String from, String to) {
        //bruker validerings sjekken for å se om input er ok eller ikke
        validateUserInput(from, to);

        String routeId = from + " - " + to;
        TripData exists = repository.getRouteById(routeId);

        if (exists != null) {
            return exists;
        }

        TripData fetched = enturPort.fetchTripFromEntur(from, to);
        repository.saveTripData(fetched);
        return fetched;
    }

    public TripData saveUserTripData(String routeId, int duration, int crowdedness, String deviations) {
        //henter turen fra entur
        TripData data = repository.getRouteById(routeId);

        if (data == null) {
            throw new IllegalArgumentException("You must search for a trip before you can save.");
        }
        if (duration < 0) throw new IllegalArgumentException("Duration must be positive");
        if (crowdedness < 0 || crowdedness > 10) throw new IllegalArgumentException("Crowdedness must be between 0 and 10.");

        //legger til brukeren sine data, til denne turen
        data.setTotalRouteDuration(duration);
        data.setCrowdednessLevel(crowdedness);
        data.setDeviations(deviations);

        //lagrer dataen i databasen vår
        return repository.saveTripData(data);
    };
}
