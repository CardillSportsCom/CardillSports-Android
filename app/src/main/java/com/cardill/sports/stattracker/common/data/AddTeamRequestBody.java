package com.cardill.sports.stattracker.common.data;

import java.util.List;

public class AddTeamRequestBody {
    private String name;
    private List<String> players;

    public AddTeamRequestBody(String name, List<String> players) {
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public List<String> getPlayers() {
        return players;
    }
}

