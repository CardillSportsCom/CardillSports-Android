package com.cardill.sports.stattracker.network;

/**
 * Created by vithushan on 10/13/18.
 */

public class AuthRequestBody {
    private String token;

    public AuthRequestBody(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
