import java.util.Scanner;
package org.travel;

class FraogTilInput {
    private int fra;
    private int til;

    public void setFra(int fra) {
        this.fra = fra;
    }

    public int getFra() {
        return fra;
    }

    public void setTil(int til) {
        this.til = til;
    }

    public int getTil() {
        return til;
    }

    public static int Frastopp(int fra)  {
        Scanner Frastopp = new Scanner(System.in);
        System.out.println("Start");
        fra = Frastopp.nextInt();
        return fra;
    }
    public static int Tilstopp(int til)  {

        Scanner Tilstopp = new Scanner(System.in);
        System.out.println("Slutt stopp");
        til = Tilstopp.nextInt();
        return til;
    }

}