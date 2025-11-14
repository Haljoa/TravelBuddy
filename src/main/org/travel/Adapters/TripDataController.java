package org.travel.Adapters;
import org.travel.Core.Domain.TripData;
import org.travel.Core.Service.TripDataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripDataController {

    private final TripDataService tripDataService;

    public TripDataController(TripDataService tripDataService) {
        this.tripDataService = tripDataService;
    }

    @GetMapping
    public List<TripData> getAllTrips() {
        return tripDataService.getAllTrips();
    }

    @GetMapping("/{routeId}")
    public TripData getTripById(@PathVariable String routeId) {
        return tripDataService.getRouteById(routeId);
    }

    @PostMapping
    public String createTrip(@RequestBody TripData tripData) {
        tripDataService.saveTripData(tripData);
        return "Trip lagret: " + tripData.getRouteId();
    }
}