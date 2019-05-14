package com.cardill.sports.stattracker.common.data;

//TODO why cant this be a field inside player
public class GamePlayer {

    Player player;
    boolean isTeamOne;
    private boolean teamTwo;

    public GamePlayer(Player player, boolean isTeamOne, boolean teamTwo) {
        this.player = player;
        this.isTeamOne = isTeamOne;
        this.teamTwo = teamTwo;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isTeamOne() {
        return isTeamOne;
    }

    public boolean isTeamTwo() {
        return teamTwo;
    }
}
