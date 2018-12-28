package com.cardill.sports.stattracker.league;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LeagueRepository {

    private static final String SHARED_PREFS_LEAGUE_ID_KEY = "shared-prefs-league-id-key";
    private static final String SHARED_PREFS_NAME = "Cardill";
    private Context context;

    public LeagueRepository(Context context) {
        this.context = context;
    }

    public String getActiveLeagueKey() {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String activeLeagueId = sharedPref.getString(SHARED_PREFS_LEAGUE_ID_KEY, "");
        return activeLeagueId;
    }

    public void saveActiveLeaguekey(String leagueId) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SHARED_PREFS_LEAGUE_ID_KEY, leagueId);
        editor.apply();
    }
}
