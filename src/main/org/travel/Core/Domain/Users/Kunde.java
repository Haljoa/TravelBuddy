package org.travel.Core.Domain.Users;

public class Kunde extends Bruker{

    final int AccessLevel = 1;

    public Kunde(String username) {
        super(username);
    }
}
