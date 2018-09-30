package com.cardill.sports.stattracker.scores.model;

import com.cardill.sports.stattracker.scores.model.boxscore.Player;

import java.io.Serializable;
import java.util.Map;

public class GameDayStatTotal implements Serializable {
    private Player player;
    private Map<String, Integer> playerTotalStats;

    public Player getPlayer() { return player; }
    public void setPlayer(Player value) { this.player = value; }

    public Map<String, Integer> getPlayerTotalStats() { return playerTotalStats; }
    public void setPlayerTotalStats(Map<String, Integer> value) { this.playerTotalStats = value; }
}
