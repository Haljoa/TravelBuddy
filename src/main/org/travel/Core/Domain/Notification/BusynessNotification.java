package org.travel.Core.Domain.Notification;

public class BusynessNotification extends Notification {
    //Busyness Notification. RouteId is which line and Busyness is most likely going to be a scale 1-100.
    private int RouteID;
    private int Busyness;

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public int getBusyness() {
        return Busyness;
    }

    public void setBusyness(int busyness) {
        Busyness = busyness;
    }

    public BusynessNotification(String description, int notificationID, int RouteID, int Busyness) {
        super(description, notificationID);
        this.Busyness = Busyness;
        this.RouteID = RouteID;

    }
}
