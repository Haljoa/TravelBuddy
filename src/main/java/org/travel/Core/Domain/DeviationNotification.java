package main.java.org.travel.Core.Domain;

public class DeviationNotification extends Notifiaction{
 //Deviation Notification is when a line becomes unavailable, deviations is the reason for the deviation
    private final int RouteID;
    private final String deviations;
    public DeviationNotification(String description, int notificationID, int routeID, String deviations) {
        super(description, notificationID);
        this.RouteID = routeID;
        this.deviations = deviations;
    }

    public String getDeviations() {
        return deviations;
    }

    public int getRouteID() {
        return RouteID;
    }
}
