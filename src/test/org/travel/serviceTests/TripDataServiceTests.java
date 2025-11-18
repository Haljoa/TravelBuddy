package org.travel.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.travel.Core.Domain.EnturTrip;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Ports.EnturTripDataPort;
import org.travel.Core.Ports.TripDataRepository;
import org.travel.Core.Service.TripDataService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//enhets tester for service klassen
//bruker Mockito til å "etterligne" eller "mocke" hvordan den snakker med in og out-porten.
//dette gjør at vi da kan teste metodene i klassen isolert fra omverdenen, slik at vi
//kan verifisere at de gjør det de skal, uten at vi trenger å koble oss til noe eksternt,
//og at den snakker med portene på rett måte.
public class TripDataServiceTests {

    //Lager mocker av portene og gir den til service klassen
    //Service klassen skal kalle de korrekte/ønskede metodene til portene,
    //den skal sende data på rett måte, og den skal returnere resulter på rett måte
    //derfor tester vi denne klassen. De andre klassene i denne kjeden testes via integrasjonstestene.
    private TripDataRepository repository;
    private TripDataService service;
    private EnturTripDataPort enturPort;

    //bruker Mockito til å lage falske versjoner av portene, og kobler dem til service klassen.
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(TripDataRepository.class);
        enturPort = Mockito.mock(EnturTripDataPort.class);
        service = new TripDataService(repository, enturPort);
    }

    //tester at saveTripData kaller out-porten på rett måte, og at dataen den sender blir mottatt.
    @Test
    void saveTripDataShouldCallSaveTripDataMethodInRepository() {
        //arrange
        TripData trip = new TripData("a - b", 10, 4, "none", new EnturTrip());

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
        TripData expectedTrip = new TripData("c - d", 12, 3, "traffic", new EnturTrip());
        //her lager vi en stub av out-porten.
        //vi bruker when til å endre oppførselen dens, slik at når den får kallet om å
        //bruke getRouteById metoden dens, vil den alltid returnere en verdi vi har bestemt.
        //vi gjør altså oppførselen dens helt forutsigbar, som er essensielt for testingen.
        when(repository.getRouteById("c - d")).thenReturn(expectedTrip);

        //act
        TripData result = service.getRouteById("c - d");

        //assert
        //sjekker først at objektet som ble returnert er det samme som vi sendte det.
        assertEquals(expectedTrip, result);
        //verify sjekker at out-porten sin metode fikk det rette kallet
        verify(repository, times(1)).getRouteById("c - d");
    }

    //sjekker at getAllTrips returnerer en liste med de samme objektene vi sendte.
    @Test
    void getAllTripsShouldReturnAllTripsFromTheRepository() {
        //arrange
        List<TripData> expectedTrips = List.of(
                new TripData("e - f", 15, 9, "none", new EnturTrip()),
                new TripData("g - h", 7, 1, "none", new EnturTrip())
        );
        //igjen lager vi en stub av out-porten.
        //når getAllTrips metoden kalles, vil out-porten returnere denne listen.
        when(repository.getAllTrips()).thenReturn(expectedTrips);

        //act
        List<TripData> result = service.getAllTrips();

        //assert
        //sjekker først at listen vi fikk tilbake er den samme vi "stubbet" :)
        assertEquals(expectedTrips.size(), result.size());
        assertSame(expectedTrips, result);
        //sjekker så at korrekt metode i out-porten fikk kallet.
        verify(repository, times(1)).getAllTrips();
    }

    //sjekker at getOrFetchTrip returnerer en trip som allerede er lagret i databasen vår
    @Test
    void getOrFetchTripShouldReturnExistingTrip() {
        //arrange
        //lager en stub av out-porten slik at den kan returnere data som "allerede ligger i db".
        TripData existingTrip = new TripData("i - j", 45, 10, "weird vibes", new EnturTrip());
        when(repository.getRouteById("i - j")).thenReturn(existingTrip);

        //act
        TripData result = service.getOrFetchTrip("i", "j");

        //assert
        //sjekker om den eksisterende turen er den samme som ble returnert av kallet
        assertSame(existingTrip, result);
        //sjekker at den ikke prøvde å hente en ny tur
        verify(enturPort, never()).fetchTripFromEntur(any(), any());
    }

    //sjekker at getOrFetchTrip kaller entur apiet for en ny tur, og deretter lagrer denne turen
    @Test
    void getOrFetchTripShouldFetchAndSaveNewTrip() {
        //arrange
        //stubber at turen ikke finnes allerede
        when(repository.getRouteById("k - l")).thenReturn(null);
        //stubber så en tur fra entur
        TripData fetched = new TripData("k - l", 5, 7, "none", new EnturTrip());
        when(enturPort.fetchTripFromEntur("k", "l")).thenReturn(fetched);

        //act
        TripData result = service.getOrFetchTrip("k", "l");

        //assert
        //sjekker at den hentet rett objekt
        assertSame(fetched, result);
        //sjekker at porten fikk det rette kallet, og at den prøvde å kontakte det eksterne apiet
        verify(enturPort).fetchTripFromEntur("k", "l");
        //sjekker at den nye turen ble lagret
        verify(repository).saveTripData(fetched);
    }

    //sjekker at saveUserTripData kan finne rett objekt i db, og så overskrive det med ny data
    @Test
    void saveUserTripDataShouldUpdateExistingData() {
        //arrange
        //stubber/lager en "eksisterende" tur
        TripData existingTrip = new TripData("m - n", 0, 0, "none", new EnturTrip());
        when(repository.getRouteById("m - n")).thenReturn(existingTrip);
        //stubber at lagringen av turen returnerer den samme turen
        when(repository.saveTripData(existingTrip)).thenReturn(existingTrip);

        //act
        TripData result = service.saveUserTripData("m - n", 35, 10, "Crowded");

        //assert
        //sjekker at oppdateringen av den eksisterende dataen fungerte
        assertEquals(35, result.getTotalRouteDuration());
        assertEquals(10, result.getCrowdednessLevel());
        assertEquals("Crowded", result.getDeviations());
        //sjekker at objektet fortsatt er trygt og "lagret" etter oppdateringene
        verify(repository).saveTripData(existingTrip);
    }

    //sjekker at hvis turen ikke ligger lagret hos oss, ingen tur er hentet fra entur enda, blir feilen håndtert korrekt
    @Test
    void saveUserTripDataThrowsErrorIfNoTripIsFound() {
        //arrange
        //stubber porten slik at vi ikke får noen tur tilbake
        when(repository.getRouteById("o - p")).thenReturn(null);

        //act
        //tvinger kastingen av en feilmelding
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.saveUserTripData("o - p", 23, 5, "none")
        );

        //assert
        //sjekker at vi får rett feilmelding tilbake
        assertEquals("You must search for a trip before you can save.", exception.getMessage());
    }

    //sjekker boundary verdier for crowdednessLevel, siden disse er satt til min og max verdier
    @Test
    void saveUserTripDataShouldAcceptCrowdednessBoundaryValues() {
        //arrange
        //lager en "eksisterende" tur, og stubber den som respons
        TripData existingTrip = new TripData("a - b", 0, 0, "none", new EnturTrip());
        when(repository.getRouteById("a - b")).thenReturn(existingTrip);
        when(repository.saveTripData(existingTrip)).thenReturn(existingTrip);

        //act og assert
        //sjekker først at 0 er akseptert
        TripData lower = service.saveUserTripData("a - b", 10, 0, "traffic");
        assertEquals(0, lower.getCrowdednessLevel());
        //sjekker så at 10 er akseptert
        TripData higher = service.saveUserTripData("a - b", 2, 10, "all good");
        assertEquals(10, higher.getCrowdednessLevel());
        //sjekker at porten fikk kallet begge gangene
        verify(repository, times(2)).saveTripData(existingTrip);
    }

    //Tester edge-cases under her.
    //mange av disse trenger ikke hele arrange, act, assert formatet, siden de ikke trenger hele set-uppet som
    //testene ovenfor f.eks. De går fortsatt gjennom stegene, for det meste act og assert, i løpet av testene,
    //men siden disse for det meste er sammensatt til samme kode-blokk, er det ikke like nødvendig å skrive det opp slik som ovenfor.
    //de følger altså fortsatt dette formatet, bare på en mye mer kompakt måte.
    //her testes edge-cases av input validering, samt error håndtering.

    @Test
    void whenFromOrToIsNullGetOrFetchTripShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.getOrFetchTrip(null, "b"));
        assertThrows(IllegalArgumentException.class, () -> service.getOrFetchTrip("a", null));
    }

    @Test
    void fromOrToIsEmptyGetOrFetchTripShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> service.getOrFetchTrip("", "b"));
        assertThrows(IllegalArgumentException.class, () -> service.getOrFetchTrip("a", ""));
    }

    @Test
    void durationIsNegativeSaveUserTripDataShouldThrowException() {
        //stubber en respons
        when(repository.getRouteById("a")).thenReturn(new TripData("a", 0, 0, "none", null));
        assertThrows(IllegalArgumentException.class, () -> service.saveUserTripData("a", -1, 5, "is fine"));
    }

    @Test
    void crowdednessOutOfRangeSaveUserTripDataShouldThrowException() {
        //stubber respons
        when(repository.getRouteById("a")).thenReturn(new TripData("a", 0, 0, "none", null));
        assertThrows(IllegalArgumentException.class, () -> service.saveUserTripData("a", 10, -1, "alright"));
        assertThrows(IllegalArgumentException.class, () -> service.saveUserTripData("a", 10, 100, "alright"));
    }

    @Test
    void enturReturnsNullAsTripSaveUserTripDataShouldThrow() {
        //stubber respons
        when(enturPort.fetchTripFromEntur("a", "b")).thenReturn(null);
        assertThrows(RuntimeException.class, () -> service.saveUserTripData("a - b", 10, 4, "none"));
    }
}
