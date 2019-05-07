package com.cardill.sports.stattracker.creator.teamselection.ui;

import com.cardill.sports.stattracker.creator.teamselection.data.Team;

class TeamEvent {
    private Team team;

    public TeamEvent(Team team) {

        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
