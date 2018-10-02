package com.cardill.sports.stattracker.teamselection.ui;

import com.cardill.sports.stattracker.stats.data.Player;

import java.util.List;

class TeamEvent {
    private List<Player> players;

    public TeamEvent(List<Player> players) {

        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
