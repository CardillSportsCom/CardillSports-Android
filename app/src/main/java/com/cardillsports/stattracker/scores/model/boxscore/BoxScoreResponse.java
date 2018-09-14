package com.cardillsports.stattracker.scores.model.boxscore;

public class BoxScoreResponse {
    private GameStat[] gameStats;

    public GameStat[] getGameStats() { return gameStats; }
    public void setGameStats(GameStat[] value) { this.gameStats = value; }
}
