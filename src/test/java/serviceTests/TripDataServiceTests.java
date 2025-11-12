package java.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.travel.Core.Domain.TripData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripDataServiceTests {
    //noe galt med pakkestrukturen vår får ikke kjørt disse

    //Lager en mock av out-porten/repositoriet og gir den til service klassen
    //Service klassen skal kalle de korrekte/ønskede metodene gjennom out-porten,
    //den skal sende data gjennom den på rett måte, og den skal returnere resulter fra porten på rett måte
    //derfor tester vi denne klassen. De andre klassene i denne kjeden testes via integrasjonstestene.
    private org.travel.Core.Ports.TripDataRepository repository;
    private org.travel.Core.Service.TripDataService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(org.travel.Core.Ports.TripDataRepository.class);
        service = new org.travel.Core.Service.TripDataService(repository);
    }

    @Test
    void saveTripDataShouldCallSaveTripDataMethodInRepository() {
        //arrange
        org.travel.Core.Domain.TripData trip = new TripData(
                "Route5", 10, List.of(5, 5), 0.4, "none"
        );

        //act
        service.saveTripData(trip);

        //assert
        //sjekker at service klassen snakker med out-porten/repositoriet på rett måte
        verify(repository, times(1)).saveTripData(trip);
    }

    @Test
    void getRouteByIdShouldReturnTripFromRepository() {
        //arrange
        TripData expectedTrip = new TripData(
                "Route6", 12, List.of(6, 6), 0.3, "traffic"
        );
        when(repository.getRouteById("Route6")).thenReturn(expectedTrip);

        //act
        TripData result = service.getRouteById("Route6");

        //assert
        assertEquals(expectedTrip, result);
        verify(repository, times(1)).getRouteById("Route6");
    }

    @Test
    void getAllTripsShouldReturnAllTripsFromTheRepository() {
        //arrange
        List<TripData> expectedTrips = List.of(
                new TripData("Route7", 15, List.of(5, 5, 5), 0.9, "none"),
                new TripData("Route8", 7, List.of(4, 3), 0.1, "none")
        );
        when(repository.getAllTrips()).thenReturn(expectedTrips);

        //act
        List<TripData> result = service.getAllTrips();

        //assert
        assertEquals(expectedTrips.size(), result.size());
        assertEquals(expectedTrips, result);
        verify(repository, times(1)).getAllTrips();
    }
}
