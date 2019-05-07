package com.cardill.sports.stattracker.main;

import com.cardill.sports.stattracker.common.league.League;

import java.util.List;

interface MainViewBinder {
    void showLeaguePicker();

    void setLeagues(List<League> leagueList);

    void launchSignInIntent();
}
