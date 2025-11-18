package org.travel.controllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.travel.Adapters.GlobalExceptionHandler;
import org.travel.Adapters.TripDataController;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Service.TripDataService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TripDataController.class)
@Import(GlobalExceptionHandler.class)
public class TripDataControllerTests {
    //tester for http funksjonalitet, get og post requester
    //MockMvc er fra spring, og lar oss simulere http requester til deres kontrollere.
    //alt gjøres i minnet, på samme måte som vi testet databasen vår
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripDataService service;

    //@BeforeEach
    //void setup() {
    //    //mocker service klassen
    //    service = mock(TripDataService.class);
    //    //bruker MockMvc til å lage et test-område
    //    mvc = MockMvcBuilders.standaloneSetup(new TripDataController(service)).build();
    //}

    @Test
    void getAllTripsShouldReturnOkAndJson() throws Exception {
        //arrange
        //stubber en respons
        when(service.getAllTrips()).thenReturn(List.of(new TripData("a", 0, 0, "none", null)));

        //act og assert
        //sjekker get request respons og strukturen på json
        mvc.perform(get("/api/trips")).andExpect(status().isOk()).andExpect(jsonPath("$[0].routeId").value("a"));
    }

    @Test
    void saveTripShouldReturnSavedTrip() throws Exception {
        //arrange
        //stubber en respons
        TripData saved = new TripData("a", 20, 3, "traffic", null);
        when(service.saveUserTripData("a", 20, 3, "traffic")).thenReturn(saved);
        //json format for responsen
        String json = """
                    {"routeId":"a","totalDuration":20,"crowdednessLevel":3,"deviations":"traffic"}
                """;

        //act og assert
        //simulerer post request og sjekker at json er korrekt
        mvc.perform(post("/api/trips/save").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk()).andExpect(jsonPath("$.routeId").value("a"))
                .andExpect(jsonPath("$.totalRouteDuration").value(20))
                .andExpect(jsonPath("$.crowdednessLevel").value(3))
                .andExpect(jsonPath("$.deviations").value("traffic"));
    }

    @Test
    void getRouteByIdShouldReturnTrip() throws Exception {
        //arrange
        //stubber en respons fra service klassen
        TripData trip = new TripData("a - b", 12, 3, "none", null);
        when(service.getRouteById("a - b")).thenReturn(trip);

        //act og assert
        //get request skal gi ok, og sende tilbake turen
        mvc.perform(get("/api/trips/{id}", "a - b"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.routeId").value("a - b"));
        //sjekker at service fikk kallet
        verify(service, times(1)).getRouteById("a - b");
    }

    @Test
    void saveTripShouldReturnBadRequestWhenMissingFields() throws Exception {
        //arrange
        //json payload med feil struktur
        String json = """
                    {"routeId":"a"}
                """;

        //act og assert
        //spring bør ikke godta denne payloaden
        mvc.perform(post("/api/trips/save").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    //denne testen er for hvis noe går galt med service klassen når den prøver lagre dataen som sendes
    //så vi kan se at DTO blir tolket rett, kontrolleren kaller service, og kaster server error
    @Test
    void saveTripShouldReturnServerErrorWhenServiceThrowsException() throws Exception {
        //arrange
        //korrekt json payload
        String json = """
                    {"routeId":"a","totalDuration":10,"crowdednessLevel":3,"deviations":"none"}
                """;
        //stubber at service klassen kaster en exception når kontrolleren prøver å lagre
        when(service.saveUserTripData(anyString(), anyInt(), anyInt(), anyString()))
                .thenThrow(new RuntimeException("Database Error."));

        //act og assert
        //post-request, returnering av korrekt server error (http 500)
        mvc.perform(post("/api/trips/save").contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().is5xxServerError()).andDo(print());
        //printer denne siden jeg måtte debugge denne testen ganske lenge før jeg fikk den til å virke.
        //hadde problemer med at GlobalExceptionHandler ble ignorert pga. at jeg manuelt opprettet en ny
        //kontroller i @BeforeEach.
    }

    //her tester vi at selve requesten som sendes er feil, og at det håndteres korrekt
    //service klassen skal ikke kalles hvis DTOet ikke er korrekt
    @Test
    void saveTripShouldReturnBadRequestWhenDTOValidationFails() throws Exception {
        //arrange
        //lager en payload med ukorrekt json formatering
        String invalidJson = """
                    {"routeId":"a","totalDuration":0}
                """;

        //act og assert
        //valideringen av daten skal skje før sevice klassen blir kallt
        //dette er noe spring skal gjøre automatisk
        mvc.perform(post("/api/trips/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson)).andExpect(status().isBadRequest());
        //sjekker at service klassen ikke fikk et kall
        verify(service, never()).saveUserTripData(anyString(), anyInt(), anyInt(), anyString());
    }
}
