package org.travel.Core.Domain.Notification;


public class CommonTripDeviationNotification extends Notification {
 private final int UserID;
 private final int routeID;

    public CommonTripDeviationNotification(String description, int notifiactionID, int UserID, int routeID) {
        super(description, notifiactionID);
        this.UserID = UserID;
        this.routeID = routeID;
    }

    public int getRouteID() {
        return routeID;
    }

    public int getUserID() {
        return UserID;
    }
}
