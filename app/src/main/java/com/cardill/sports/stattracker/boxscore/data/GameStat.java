package com.cardill.sports.stattracker.boxscore.data;

public class GameStat {
    private String teamName;
    private PlayerStat[] playerStats;

    public String getTeamName() { return teamName; }
    public void setTeamName(String value) { this.teamName = value; }

    public PlayerStat[] getPlayerStats() { return playerStats; }
    public void setPlayerStats(PlayerStat[] value) { this.playerStats = value; }
}