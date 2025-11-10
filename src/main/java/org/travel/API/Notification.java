package org.travel.API;
import java.util.HashMap;
import java.util.Objects;

public class Notification {


    private int NotificationID;
    private String NotificationDiscription;

    public Notification(int NotificationID, String VarslingDiscription) {
        this.NotificationID = NotificationID;
        this.NotificationDiscription = VarslingDiscription;
    }

    public Notification() {}

    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int notificationID) {
        NotificationID = notificationID;
    }

    public String getNotificationDiscription() {
        return NotificationDiscription;
    }

    public void setNotificationDiscription(String notificationDiscription) {
        NotificationDiscription = notificationDiscription;
    }


    public static Notification UserNotification(int NotificationID) {

        HashMap<Integer, String> Notification = new HashMap<Integer, String>();

//     Add keys and values (NotificationID, NotificationDiscription)
        Notification.put(1, "Forsinkelse");
        Notification.put(2, "Avvik på stasjon");
        Notification.put(3, "Økt travelhet");
        Notification.put(4, "Neste stasjon er avgangsstoppet");
        Notification.put(5, "Neste er transportmiddelet");
        Notification.put(6, "Bombetrussel");

        if (Objects.equals(Notification.get(NotificationID), null))
        {
            return new Notification(NotificationID, "error");
        }

        return new Notification(NotificationID, Notification.get(NotificationID));

    }
}

