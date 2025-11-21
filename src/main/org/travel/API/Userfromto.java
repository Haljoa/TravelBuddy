package org.travel.API;
import java.util.Scanner;

class Userfromto {
    private String from;
    private String to;

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }

    public static String  Fromstop(String from)  {
        Scanner Fromstop = new Scanner(System.in);
        System.out.println("Start");
        from = Fromstop.next();
        return from;
    }
    public static String Tostop(String to)  {

        Scanner Tostop = new Scanner(System.in);
        System.out.println("Slutt stopp");
        to = Tostop.next();
        return to;
    }

}