package org.travel.Core.Domain.Notification;

public class Notification {

    private final String Description;
    private final int NotificationID;
//baseline info used in all notifications.

    public Notification(String description, int notifiactionID) {
        this.Description = description;
        this.NotificationID = notifiactionID;
    }

    public String getDescription() {
        return Description;
    }

    public int getNotificationID() {
        return NotificationID;
    }
}
