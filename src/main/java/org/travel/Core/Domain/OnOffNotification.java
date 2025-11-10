package main.java.org.travel.Core.Domain;

public class OnOffNotification extends Notifiaction{

    private final int UntilOnOff;
    private final int RouteID;
    private final int StopID;


    public int getStopID() {
        return StopID;
    }
    public int getRouteID() {
        return RouteID;
    }
    public int getUntilOnOff() {
        return UntilOnOff;
    }

    public OnOffNotification(String description, int notificationID, int untilOnOff, int RouteID, int StopID) {
        super(description,notificationID);
        this.UntilOnOff = untilOnOff;
        this.RouteID = RouteID;
        this.StopID = StopID;
    }
}
