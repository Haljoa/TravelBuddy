package org.travel.Core.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//denne klassen er et DTO som representerer hvordan dataen som kommer fra brukerinput fra nettsiden skal være.
//@min, @NotNull, osv., passer på at feil-formatert data ikke kommer inn til kontrolleren, men blir fanget opp i forkant av dette.
//brukt av TripDataController for bevege data inn i systemet.
public class TripUserInputDTO {

    @NotBlank
    public String routeId;

    @NotNull
    @Min(1)
    public int totalDuration;

    @NotNull
    @Min(0)
    public int crowdednessLevel;

    @NotBlank
    public String deviations;
}
