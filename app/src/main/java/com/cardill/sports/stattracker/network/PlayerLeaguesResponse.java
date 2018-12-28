package com.cardill.sports.stattracker.network;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlayerLeaguesResponse {
    private String message;
    private List<LeagueResponse> leagues;

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String value) {
        this.message = value;
    }

    @JsonProperty("leagues")
    public List<LeagueResponse> getLeagues() {
        return leagues;
    }

    @JsonProperty("leagues")
    public void setLeagues(List<LeagueResponse> value) {
        this.leagues = value;
    }
}
