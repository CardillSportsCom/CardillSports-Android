package com.cardill.sports.stattracker.stats.data;

import com.cardill.sports.stattracker.common.data.User;

//TODO use a json deserializer so you can rename this variable
// right now the variable name has to match what the backend sends us
public class LeagueStat {
    private User player;
    private PlayerTotalStats playerTotalStats;

    public User getUser() { return player; }
    public void setUser(User value) { this.player = value; }

    public PlayerTotalStats getPlayerTotalStats() { return playerTotalStats; }
    public void setPlayerTotalStats(PlayerTotalStats value) { this.playerTotalStats = value; }
}
