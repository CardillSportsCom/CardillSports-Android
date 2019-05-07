package com.cardill.sports.stattracker.common.auth;

/**
 * Created by vithushan on 10/16/18.
 */

public class AuthPlayer {

    String firstName;
    String lastName;
    String email;
    String id;

    public AuthPlayer(String firstName, String lastName, String email, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
