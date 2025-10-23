package main.java.org.travel.core.service;

import main.java.org.travel.core.Ports.TripDataRepository;
import main.java.org.travel.core.domain.TripData;

import java.util.List;

//Denne klassen er service klassen for hele TripData "familien", og til databasen vår. av alle klassene i denne kjeden,
// fra TripData (som er hva vi her ønsker å lage objekter av, og å kunne sende til, og hente fra databasen)
// til databasen selv, er det denne klassen man bruker når man ønsker å gjøre CRUD operasjoner.
//man kaller ikke f.eks. en adapter, her TripDataMongoAdapter, eller en port, her TripDataRepository, direkte for
//dette, man gjør det gjennom en service klasse som denne, som deretter sender instruksjonene videre.

//Kjeden selv går slik: Main (eller da et UI eller REST API), kaller -> TripDataService, som gjennom porten ->
//TripDataRepository, videre kaller -> TripDataMongoAdapter, som er kobligen til -> MongoDB.
//Når en CRUD operasjon skjer, er det denne stien den må gjennom, og følger.

//trenger kanskje ikke Jackson lenger, må se om det brukes andre steder i systemet før jeg fjerner det fra dependencies

public class TripDataService {
    //for å snakke med porten / interfacet
    private final TripDataRepository repository;
    public TripDataService(TripDataRepository repository) {
        this.repository = repository;
    }

    //for lagring av TripData til repository (sende det dertil)
    public void saveTripData(TripData tripData) {
        repository.saveTripData(tripData);
    }

    //for henting av info om en rute via ID
    public TripData getRouteById(String routeId) {
        return repository.findRouteById(routeId);
    }

    //hente alle ruter og info
    public List<TripData> getAllTrips() {
        return repository.findAllTrips();
    }
}
