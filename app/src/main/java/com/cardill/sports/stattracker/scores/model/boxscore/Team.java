package com.cardill.sports.stattracker.scores.model.boxscore;

public class Team {
    private String[] players;
    private Object[] games;
    private String id;
    private String name;
    private String dateCreated;
    private long v;

    public String[] getPlayers() { return players; }
    public void setPlayers(String[] value) { this.players = value; }

    public Object[] getGames() { return games; }
    public void setGames(Object[] value) { this.games = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String value) { this.dateCreated = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }
}
