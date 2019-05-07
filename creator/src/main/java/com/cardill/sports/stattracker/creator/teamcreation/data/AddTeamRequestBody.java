package com.cardill.sports.stattracker.creator.teamcreation.data;

import java.util.List;

public class AddTeamRequestBody {
    private String name;
    private List<String> players;
    private String leagueId;

    public AddTeamRequestBody(String name, List<String> players, String leagueId) {
        this.name = name;
        this.players = players;
        this.leagueId = leagueId;
    }

    public String getName() {
        return name;
    }

    public List<String> getPlayers() {
        return players;
    }

    public String getLeagueId() {
        return leagueId;
    }
}

