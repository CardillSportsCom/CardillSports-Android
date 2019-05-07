package com.cardill.sports.stattracker.creator.teamselection.data;

public class TeamResponse {
    private String message;
    private Team[] teams;

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] value) {
        this.teams = value;
    }
}

