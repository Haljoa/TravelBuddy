package org.travel.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.travel.Core.Domain.TripData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//enhets tester for service klassen
//bruker Mockito til å "etterligne" eller "mocke" hvordan den snakker med out-porten.
//dette gjør at vi da kan teste metodene i klassen isolert fra omverdenen, slik at vi
//kan verifisere at de gjør det de skal, uten at vi trenger å koble oss til noe eksternt,
//og at den snakker med out-porten på rett måte.
public class TripDataServiceTests {

    //Lager en mock av out-porten/repositoriet og gir den til service klassen
    //Service klassen skal kalle de korrekte/ønskede metodene gjennom out-porten,
    //den skal sende data gjennom den på rett måte, og den skal returnere resulter fra porten på rett måte
    //derfor tester vi denne klassen. De andre klassene i denne kjeden testes via integrasjonstestene.
    private org.travel.Core.Ports.TripDataRepository repository;
    private org.travel.Core.Service.TripDataService service;

    //bruker Mockito til å lage en falsk versjon av out-porten, og kobler den til service klassen.
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(org.travel.Core.Ports.TripDataRepository.class);
        service = new org.travel.Core.Service.TripDataService(repository);
    }

    //tester at saveTripData kaller out-porten på rett måte, og at dataen den sender blir mottatt.
    @Test
    void saveTripDataShouldCallSaveTripDataMethodInRepository() {
        //arrange
        org.travel.Core.Domain.TripData trip = new TripData(
                "Route5", 10, List.of(5, 5), 0.4, "none"
        );

        //act
        service.saveTripData(trip);

        //assert
        //sjekker at service klassen snakker med out-porten/repositoriet på rett måte.
        //verify sjekker at den falske out-porten fikk kallet om å bruke sin saveTripData metode
        //og at dataen vi sendte ble mottatt.
        verify(repository, times(1)).saveTripData(trip);
    }

    //sjekker at når vi spør etter en rute basert på IDen dens, får vi rett objekt tilbake
    @Test
    void getRouteByIdShouldReturnTripFromRepository() {
        //arrange
        TripData expectedTrip = new TripData(
                "Route6", 12, List.of(6, 6), 0.3, "traffic"
        );
        //her lager vi en stub av out-porten.
        //vi bruker when til å endre oppførselen dens, slik at når den får kallet om å
        //bruke getRouteById metoden dens, vil den alltid returnere en verdi vi har bestemt.
        //vi gjør altså oppførselen dens helt forutsigbar, som er bra for testingen.
        when(repository.getRouteById("Route6")).thenReturn(expectedTrip);

        //act
        TripData result = service.getRouteById("Route6");

        //assert
        //sjekker først at objektet som ble returnert er det samme som vi sendte det.
        assertEquals(expectedTrip, result);
        //verify sjekker at out-porten sin metode fikk det rette kallet
        verify(repository, times(1)).getRouteById("Route6");
    }

    //sjekker at getAllTrips returnerer en liste med de samme objektene vi sendte.
    @Test
    void getAllTripsShouldReturnAllTripsFromTheRepository() {
        //arrange
        List<TripData> expectedTrips = List.of(
                new TripData("Route7", 15, List.of(5, 5, 5), 0.9, "none"),
                new TripData("Route8", 7, List.of(4, 3), 0.1, "none")
        );
        //igjen lager vi en stub av out-porten.
        //når getAllTrips metoden kalles, vil out-porten returnere denne listen.
        when(repository.getAllTrips()).thenReturn(expectedTrips);

        //act
        List<TripData> result = service.getAllTrips();

        //assert
        //sjekker først at listen vi fikk tilbake er den samme vi "stubbet" :)
        assertEquals(expectedTrips.size(), result.size());
        assertEquals(expectedTrips, result);
        //sjekker så at korrekt metode i out-porten fikk kallet.
        verify(repository, times(1)).getAllTrips();
    }
}
