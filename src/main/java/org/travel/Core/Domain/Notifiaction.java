package main.java.org.travel.Core.Domain;

public class Notifiaction {

    private final String Description;
    private final int NotifiactionID;
//baseline info used in all notifications.

    public Notifiaction(String description, int notifiactionID) {
        this.Description = description;
        this.NotifiactionID = notifiactionID;
    }

    public String getDescription() {
        return Description;
    }

    public int getNotifiactionID() {
        return NotifiactionID;
    }
}
