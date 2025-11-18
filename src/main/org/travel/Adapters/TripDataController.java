package org.travel.Adapters;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.travel.Core.DTO.TripUserInputDTO;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Service.TripDataService;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripDataController {

    private final TripDataService tripDataService;

    public TripDataController(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    //HTTP-metoder

    //GET-requests
    @GetMapping
    public List<TripData> getAllTrips() {
        return tripDataService.getAllTrips();
    }

    @GetMapping("/{routeId}")
    public TripData getTripById(@PathVariable String routeId) {
        return tripDataService.getRouteById(routeId);
    }

    @GetMapping("/search")
    public TripData getOrFetchTrip(@RequestParam String from, @RequestParam String to) {
        return tripDataService.getOrFetchTrip(from, to);
    }

    //POST-requests
    @PostMapping
    public String createTrip(@RequestBody TripData tripData) {
        tripDataService.saveTripData(tripData);
        return "Trip lagret: " + tripData.getRouteId();
    }

    @PostMapping("/save")
    public ResponseEntity<TripData> saveTrip(@Valid @RequestBody TripUserInputDTO dto) {
        TripData saved = tripDataService.saveUserTripData(
                dto.routeId,
                dto.totalDuration,
                dto.crowdednessLevel,
                dto.deviations
        );
        return ResponseEntity.ok(saved);
    }
}