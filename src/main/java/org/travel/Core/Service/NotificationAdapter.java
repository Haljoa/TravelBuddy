package org.travel.Core.Service;

import org.travel.Core.DTO.NotificationData;
import org.travel.Core.Domain.Notification.*;
import org.travel.Core.Ports.NotificationPort;

import java.util.HashMap;
/**
 * Notification assembler. Creates notification objects using the data from DTO
 * gets the description from the hashmap.
*/


public class NotificationAdapter implements NotificationPort {
    @Override
    public Notifiaction create(NotificationType Type, NotificationData data) {
        Notifiaction NewNotification = UserNotification(data.notificationID);
        String description = NewNotification.getDescription();
            switch(Type){
                case DELAY:
                    return new DelayNotification(description,data.notificationID,
                            data.routeID, data.delay);
                case BUSYNESS:
                    return new BusynessNotification(description,data.notificationID,
                            data.routeID, data.busyness);
                case DEVIATION:
                    return new DeviationNotification(description, data.notificationID,
                            data.routeID, data.deviations);
                case ON_OFF:
                    return new OnOffNotification(description,data.notificationID,
                            data.untilOnOff, data.routeID, data.stopID);
                case ACCESSIBILITY:
                    return new AccessibilityNotification(description,data.notificationID, data.stopID);
                case COMMONTRIPDEVIATION:
                    return new CommonTripDeviationNotification(description,data.notificationID, data.UserID, data.routeID);

                default:
                    throw new IllegalArgumentException("Unknown notification type" + Type);

            }
    }

    private Notifiaction UserNotification(int notificationID){
        HashMap<Integer, String> NotificationMap = new HashMap<>();
        NotificationMap.put(1, "Forsinkelse");
        NotificationMap.put(2, "Avvik på stasjon");
        NotificationMap.put(3, "Avvik på stasjon, Problemer med tilgjengelighet");
        NotificationMap.put(4, "Avvik på transportmiddel");
        NotificationMap.put(5, "Økt travelhet");
        NotificationMap.put(6, "Neste stasjon er avgangsstoppet");
        NotificationMap.put(7, "Neste er transportmiddelet");
        NotificationMap.put(8, "Avvik på din mest reiste rute");



        String description = NotificationMap.getOrDefault(notificationID, "error");
        return new Notifiaction(description, notificationID);

    }


}
