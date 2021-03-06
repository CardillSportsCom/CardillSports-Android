package com.cardill.sports.stattracker.creator.teamcreation.data;

public class AddPlayerToLeagueRequestBody {
    private String playerId;
    private String leagueId;

    public AddPlayerToLeagueRequestBody(String playerId, String leagueId) {
        this.playerId = playerId;
        this.leagueId = leagueId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getLeagueId() {
        return leagueId;
    }
}
