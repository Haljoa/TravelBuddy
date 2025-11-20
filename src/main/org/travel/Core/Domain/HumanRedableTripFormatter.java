package org.travel.Core.Domain;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

//denne klassen tar imot json data fra entur, som ikke er veldig menneske-vennlig,
//og formaterer det på en måte som er mye lettere å forstå
//den returnerer en liste med strings, hvor hver string er en linje med noe faktisk forståelig :)
public class HumanRedableTripFormatter {
    public static List<String> toHumanReadable(TripData fullTrip) {
        List<String> lines = new ArrayList<>();

        //hvis dataen mangler noe viktig, sender vi en melding
        if (fullTrip == null || fullTrip.getEnturTrip() == null || fullTrip.getEnturTrip().getTripPatterns() == null) {
            lines.add("There are currently no trips available.");
            return lines;
        }
        if (fullTrip.getEnturTrip().getTripPatterns().isEmpty()) {
            lines.add("There are currently no trips available.");
            return lines;
        }
        EnturTrip trip = fullTrip.getEnturTrip();
        int patternIndex = 1;

        //looper gjennom hver trip pattern, siden entur ofte gir flere alternative turer,
        //henter ut dataen, og setter den inn i våre egen-definerte strenger, som gir mer mening for et menneske å lese
        for (TripPattern pattern : trip.getTripPatterns()) {
            lines.add("Option " + patternIndex++ + ":");

            //bruker innsendt data
            lines.add("User-added information");
            lines.add("Actual trip duration: " + fullTrip.getTotalRouteDuration() + " minutes.");
            lines.add("Crowdedness level: " + (int) fullTrip.getCrowdednessLevel() + " / 10.");
            lines.add("Deviations: " + fullTrip.getDeviations());
            lines.add("");

            //summary av TripPattern
            lines.add("Total trip duration: " + minutes(pattern.getDuration()) + " minutes.");
            lines.add("Total walking distance: " + (int) pattern.getWalkDistance() + " meters.");
            lines.add("Legs: ");

            //looper gjennom hver "leg" av reisen, og konverterer dem til mer naturlig språk
            for (Leg leg : pattern.getLegs()) {
                //endrer ISO til det mer lesbare, timer, minutter, sekunder
                String start = simplifyTime(leg.getExpectedStartTime());
                String end = simplifyTime(leg.getExpectedEndTime());
                //tar vekk desimalene fra distansen, trenger ikke å ha 343,3 meter f.eks.
                int distance = (int) leg.getDistance();

                //hvis mode = foot, viser vi heller Walk (som gir mer mening). ellers viser vi "Take" og så transportmiddelet
                String mode = leg.getMode().equalsIgnoreCase("foot") ? "Walk" : "Take " +
                        leg.getMode();
                //hvis leg har et line objekt, viser vi "line" og så public code, ellers er denne blank
                String line = leg.getLine() != null ? " (line " + leg.getLine().getPublicCode() + ")" : "";

                lines.add("-" + mode + line + ", " + distance + " meters, from " + start + " to " + end);
            }
            lines.add("");//for luft mellom ulike patterns som entur sender
        }
        return lines;
    }
    //konvertering fra sekunder til minutter, siden entur sender sekunder
    private static int minutes(int second) {
        return (int) Math.round(second / 60.0);
    }
    //entur sender start og end i ISO-timestamp format. denne metoden konverterer dette til
    //noe mer fornuftig (for et menneske å lese), som er timer, minutter, sekunder
    private static String simplifyTime(String simple) {
        try {
            OffsetDateTime time = OffsetDateTime.parse(simple);
            return time.toLocalTime().toString();
        } catch (Exception e) {
            return simple;
        }
    }
}
