package main.java.org.travel.Core.Ports;

import main.java.org.travel.Core.Domain.*;
//transferring of dto data + the unique types of notifications so the adapter can create objects
public interface NotificationPort {
    Notifiaction create(NotificationType Type, NotificationData data);
}
