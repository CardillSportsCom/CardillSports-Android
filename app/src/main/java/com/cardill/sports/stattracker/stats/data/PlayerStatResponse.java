package com.cardill.sports.stattracker.stats.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStatResponse {
    private String message;
    private PlayerStat[] playerStats;

    @JsonProperty("message")
    public String getMessage() { return message; }
    @JsonProperty("message")
    public void setMessage(String value) { this.message = value; }

    @JsonProperty("playerStats")
    public PlayerStat[] getPlayerStats() { return playerStats; }
    @JsonProperty("playerStats")
    public void setPlayerStats(PlayerStat[] value) { this.playerStats = value; }
}