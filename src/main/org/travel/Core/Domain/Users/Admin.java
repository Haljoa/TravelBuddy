package org.travel.Core.Domain.Users;

public class Admin extends Bruker{

    final int AccessLevel = 2;

    public Admin(String username) {
        super(username);
    }
}
