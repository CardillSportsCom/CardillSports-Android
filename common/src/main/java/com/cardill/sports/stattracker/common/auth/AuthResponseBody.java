package com.cardill.sports.stattracker.common.auth;

/**
 * Created by vithushan on 10/16/18.
 */

public class AuthResponseBody {
    private String id_token;
    private AuthPlayer player;

    public AuthResponseBody(String id_token, AuthPlayer player) {
        this.id_token = id_token;
        this.player = player;
    }

    public String getId_token() {
        return id_token;
    }

    public AuthPlayer getPlayer() {
        return player;
    }
}
