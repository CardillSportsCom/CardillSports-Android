package com.cardill.sports.stattracker.user;

import android.content.SharedPreferences;

import timber.log.Timber;

public class Session {

    private SharedPreferences sharedPref;

    public Session(SharedPreferences sharedPref) {
        this.sharedPref = sharedPref;
    }

    public boolean isLoggedIn() {
        return sharedPref.getString("token-key", "").equals("");
    }

    public void saveToken(String token) {

        Timber.d("TOKEN SAVED");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token-key", token);
        editor.commit();
    }

    public String getToken() {
        return sharedPref.getString("token-key", "");
    }

    public void invalidate() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token-key", "");
        editor.commit();
    }
}