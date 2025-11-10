package main.java.org.travel.Core.Domain;

public class DelayNotification extends Notifiaction {
    //DelayNotification. RouteId is which line and delay is the amount in minutes

    private final int routeID;
    private final int delay;

    public DelayNotification(String description, int notifiactionID, int routeID, int delay) {
        super(description, notifiactionID);
        this.routeID = routeID;
        this.delay = delay;
    }
    public int getRouteID() {
        return routeID;
    }

    public int getDelay() {
        return delay;
    }
}
