package com.cardill.sports.stattracker.common.data;

public class ConsumerGamePlayer {

    ConsumerPlayer player;
    boolean isTeamOne;
    private boolean teamTwo;

    public ConsumerGamePlayer(ConsumerPlayer player, boolean isTeamOne, boolean teamTwo) {
        this.player = player;
        this.isTeamOne = isTeamOne;
        this.teamTwo = teamTwo;
    }

    public ConsumerPlayer getPlayer() {
        return player;
    }

    public boolean isTeamOne() {
        return isTeamOne;
    }

    public boolean isTeamTwo() {
        return teamTwo;
    }
}
