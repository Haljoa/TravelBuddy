package org.travel.Core.Domain.Notification;


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
    }

    public int getUserID() {
        return UserID;
    }
}
