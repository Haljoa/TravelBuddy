package org.travel.Core.Domain.Users;

public abstract class Bruker {

    final int AccessLevel = 0;
    String Username;

    public Bruker(String username) {
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
