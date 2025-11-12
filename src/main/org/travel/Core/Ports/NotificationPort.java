package org.travel.Core.Ports;

import org.travel.Core.DTO.NotificationData;
import org.travel.Core.Domain.Notification.Notification;
import org.travel.Core.Domain.Notification.NotificationType;

//transferring of dto data + the unique types of notifications so the adapter can create objects
public interface NotificationPort {
    Notification create(NotificationType Type, NotificationData data);
}
