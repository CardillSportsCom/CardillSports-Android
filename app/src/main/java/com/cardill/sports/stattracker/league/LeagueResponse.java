package com.cardill.sports.stattracker.league;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeagueResponse {
    private String id;
    private League league;

    @JsonProperty("_id")
    public String getID() { return id; }
    @JsonProperty("_id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("league")
    public League getLeague() { return league; }
    @JsonProperty("league")
    public void setLeague(League value) { this.league = value; }
}
