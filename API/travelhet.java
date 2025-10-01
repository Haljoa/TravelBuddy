import java.util.Scanner;

public class Travelhet {
    private String transportmiddel;
    private int antallPassasjerer;
    private int maksKapasitet;
    private double travelhetsNiva;
    
    public Travelhet(String transportmiddel, int maksKapasitet) {
        this.transportmiddel = transportmiddel;
        this.maksKapasitet = maksKapasitet;
        this.antallPassasjerer = 0;
        this.travelhetsNiva = 0.0;
    }


    public void registrerPassasjerer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Travelhet på " + transportmiddel + " ===");
        System.out.println("Skriv inn antall passasjerer: ");

        try {
            int input1 = scanner.nextInt();

            if (input1 < 0) {
                System.out.println("Antall passasjerer kan ikke være negativt");
                return;
            }

            this.antallPassasjerer = input1;
            // beregnTravelhet();
            // visTravelhet();
        }
        catch (Exception e) {
            System.out.println("Ugyldig input! Skriv inn et heltall");
        }
    }
    private void beregnTravelhet(){
        if (maksKapasitet == 0) {
            travelhetsNiva = 0.0;
            return;
        }

        travelhetsNiva = (double) antallPassasjerer / maksKapasitet * 100;
    }
    public void visTravelhet(){
        System.out.println("\n--- Travelhetsrapport ---")
        System.out.println("Transportmiddel: " + transportmiddel);
        System.out.println("Maks kapasitet: " + maksKapasitet);
        System.out.println("Antall passasjerer: " + antallPassasjerer);
        System.out.println("Travelhetsnivå: " + String.format("%.1f", travelhetsNiva) + "%");¨

        //Vurderer travelhetsnivå med ikoner
        if (travelhetsNiva < 50) {
            System.out.println("Status: 🟢 Lav travelhet");
        }
        else if (travelhetsNiva < 80){
            System.out.println("Status: 🟡 Moderat travelhet");
        }
        else if (travelhetsNiva < 100){
            System.out.println("Status: 🟠 Høy travelhet");
        }
        else{
            System.out.println("Status: 🔴 Overfylt");
        }
    }

    // GETTERE OG SETTERE
    public String getTransportmiddel(){
        return transportmiddel;
    }
    public void setTransportmiddel(String transportmiddel){
        this.transportmiddel = transportmiddel;
    }

    public int getAntallPassasjerer(){
        return antallPassasjerer;
    }

    public int getMaksKapasitet(){
        return maksKapasitet;
    }
    public void setMaksKapasitet(int maksKapasitet){
        this.maksKapasitet = maksKapasitet;
        beregnTravelhet(); // Reberegner travelhet ved endring av kapasitet !!
    } 

    public double getTravelhetsNiva(){
        return travelhetsNiva;
    }

    //MAIN FOR TESTING
    public static void main(String[] args){
        
    }
}