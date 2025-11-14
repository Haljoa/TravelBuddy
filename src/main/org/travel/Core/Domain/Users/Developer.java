package org.travel.Core.Domain.Users;

public class Developer extends Bruker{

    final int AccessLevel = 3;

    public Developer(String username) {
        super(username);
    }
}
