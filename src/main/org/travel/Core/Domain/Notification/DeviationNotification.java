package org.travel.Core.Domain.Notification;

public class DeviationNotification extends Notification {
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
