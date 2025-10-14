package main.java.org.travel.core.service;

import main.java.org.travel.core.domain.Route;
import main.java.org.travel.core.domain.JourneyDuration;
import main.java.org.travel.core.service.JourneyDurationService;

// Ser over en valgt rute og gir den en vanskeilighetsgrad fra 1 - 10 i kategoriene lengde, stoppmengde og travelhet
// 1 i lengde er en rute som har tar mindre enn 2 minutter fra start til slutt
// 19 i lengde er en rute som tar mer enn 2 timer fra start til slutt
// 1 i stoppmengde er en rute som har mindre enn 3 stopp fra start til slutt
// 10 i stoppmengde er en rute som har fler enn 30 stopp fra start til slutt
// 1 i travelhet betyr at ruten er vanligvis helt tom
// 10 i travelhet betyr at ruten er vanligvis stappet full og kanskje ikke har mer plass

// TODO: etter vi har en måte å finne travelhet på, legg det til i denne klassen

public class RuteVanskelighet {
    private int VanskelighetsGradLengde;
    private int VanskelighetsGradStopp;
    private int VanskelighetsGradTravelhet = 0; // Ubrukt for nå, i testing bør denne alltid være 0


    public int RuteVanskelighetKalkulator(Route route) {

        /*
        JourneyDuration Duration = new JourneyDuration(route.getRouteId(), route.getStops().getFirst(),
                route.getStops().getLast(), );
        TODO senere når jeg finner ut av hvordan å få ut JourneyDuration fra et route objekt
        switch (Duration.getDurationInMinutes) {

        }
        */

        for (int stop = 0; stop < route.getStops().size(); stop++) {
            VanskelighetsGradStopp++;
        }

        return 0;
    };

}
