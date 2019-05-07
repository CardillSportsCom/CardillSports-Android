package com.cardill.sports.stattracker.consumer.profile.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {
    private String[] players;
    private Object[] substitutes;
    private Object[] injuries;
    private Object[] games;
    private String id;
    private Name name;
    private String league;
    private String dateCreated;
    private long v;

    @JsonProperty("players")
    public String[] getPlayers() { return players; }
    @JsonProperty("players")
    public void setPlayers(String[] value) { this.players = value; }

    @JsonProperty("substitutes")
    public Object[] getSubstitutes() { return substitutes; }
    @JsonProperty("substitutes")
    public void setSubstitutes(Object[] value) { this.substitutes = value; }

    @JsonProperty("injuries")
    public Object[] getInjuries() { return injuries; }
    @JsonProperty("injuries")
    public void setInjuries(Object[] value) { this.injuries = value; }

    @JsonProperty("games")
    public Object[] getGames() { return games; }
    @JsonProperty("games")
    public void setGames(Object[] value) { this.games = value; }

    @JsonProperty("_id")
    public String getID() { return id; }
    @JsonProperty("_id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("name")
    public Name getName() { return name; }
    @JsonProperty("name")
    public void setName(Name value) { this.name = value; }

    @JsonProperty("league")
    public String getLeague() { return league; }
    @JsonProperty("league")
    public void setLeague(String value) { this.league = value; }

    @JsonProperty("dateCreated")
    public String getDateCreated() { return dateCreated; }
    @JsonProperty("dateCreated")
    public void setDateCreated(String value) { this.dateCreated = value; }

    @JsonProperty("__v")
    public long getV() { return v; }
    @JsonProperty("__v")
    public void setV(long value) { this.v = value; }
}