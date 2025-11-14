package org.travel.Core.Domain.Notification;


<<<<<<< HEAD
public class CommonTripDeviationNotification extends DeviationNotification {
 /*
 uses userid to see if your most common trip has a deviation.
if the trip has been traveled more or equal to three times in a month
it will be considered a common trip
  */

    private final int UserID;


    public CommonTripDeviationNotification(String description, int notifiactionID, int routeID, String deviations, int UserID) {
        super(description, notifiactionID, routeID, deviations);
        this.UserID = UserID;
=======
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
>>>>>>> Brukere
    }

    public int getUserID() {
        return UserID;
    }
}
