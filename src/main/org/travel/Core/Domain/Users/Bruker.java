package org.travel.Core.Domain.Users;

//Overklassen til alle de forskjellige brukertypene vi kan lage. Definerer universell funksjonalitet for alle klassene
//De forskjellige brukertypene ville hatt sine egne funksjoner, hvis vi hadde tid til å implementere det ordentlig

public abstract class Bruker {

    private final int AccessLevel = 0;
    private int UserID;
    private String Username;

    public Bruker(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setUserID() {

    }
}
