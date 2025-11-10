package main.java.org.travel.Core.Domain;



public class CommonTripDeviationNotification extends Notifiaction {
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
