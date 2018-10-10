package com.cardill.sports.stattracker.boxscore.data;

import java.io.Serializable;

/**
 * This is confusing having multiple model classes called Player.
 * Should rename this to JSONPlayer, but that would break json serialization.
 * TODO
 */
public class Player implements Serializable {
    private Object[] teams;
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateCreated;
    private long v;
    private Object teamPlayers;
    private String playerID;
    private Object[] leagues;

    public Object[] getTeams() { return teams; }
    public void setTeams(Object[] value) { this.teams = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String value) { this.firstName = value; }

    public String getLastName() { return lastName; }
    public void setLastName(String value) { this.lastName = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String value) { this.dateCreated = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }

    public Object getTeamPlayers() { return teamPlayers; }
    public void setTeamPlayers(Object value) { this.teamPlayers = value; }

    public String getPlayerID() { return playerID; }
    public void setPlayerID(String value) { this.playerID = value; }

    public Object[] getLeagues() { return leagues; }
    public void setLeagues(Object[] value) { this.leagues = value; }
}