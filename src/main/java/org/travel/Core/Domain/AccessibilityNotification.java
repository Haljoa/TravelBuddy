package main.java.org.travel.Core.Domain;

public class AccessibilityNotification extends Notifiaction{
//Notification about if there is any problems with accessibility, stopid is which stop the problem occurs
    private final int StopID;

    public int getStopID() {
        return StopID;
    }
public AccessibilityNotification(String description, int notificationID, int StopID){
    super(description, notificationID);
    this.StopID = StopID;
}
}
