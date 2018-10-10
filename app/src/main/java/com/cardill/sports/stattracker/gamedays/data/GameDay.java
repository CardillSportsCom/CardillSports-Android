package com.cardill.sports.stattracker.gamedays.data;

import java.io.Serializable;

/**
 * Created by vithushan on 9/12/18.
 */

public class GameDay implements Serializable {
    private String gameDate;
    private Game[] games;
    private GameDayStatTotal[] gameDayStatTotals;

    public String getGameDate() { return gameDate; }
    public void setGameDate(String value) { this.gameDate = value; }

    public Game[] getGames() { return games; }
    public void setGames(Game[] value) { this.games = value; }

    public GameDayStatTotal[] getGameDayStatTotals() { return gameDayStatTotals; }
    public void setGameDayStatTotals(GameDayStatTotal[] value) { this.gameDayStatTotals = value; }
}
