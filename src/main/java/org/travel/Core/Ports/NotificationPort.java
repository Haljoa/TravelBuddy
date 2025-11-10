package org.travel.Core.Ports;

import main.java.org.travel.Core.DTO.NotificationData;
import main.java.org.travel.Core.Domain.Notification.Notifiaction;
import main.java.org.travel.Core.Domain.Notification.NotificationType;

//transferring of dto data + the unique types of notifications so the adapter can create objects
public interface NotificationPort {
    Notifiaction create(NotificationType Type, NotificationData data);
}
