package com.cardill.sports.stattracker.stats.data;

public class LeagueStat {
    private Player player;
    private PlayerTotalStats playerTotalStats;

    public Player getPlayer() { return player; }
    public void setPlayer(Player value) { this.player = value; }

    public PlayerTotalStats getPlayerTotalStats() { return playerTotalStats; }
    public void setPlayerTotalStats(PlayerTotalStats value) { this.playerTotalStats = value; }
}
