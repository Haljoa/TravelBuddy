package org.travel.Core.Domain.Users;

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
