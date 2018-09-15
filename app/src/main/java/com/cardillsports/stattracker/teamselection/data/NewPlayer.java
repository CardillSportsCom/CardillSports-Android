package com.cardillsports.stattracker.teamselection.data;

public class NewPlayer {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dateCreated;
    private Object[] teams;
    private String id;
    private long v;
    private Object teamPlayers;
    private String newPlayerID;

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

    public Object[] getTeams() { return teams; }
    public void setTeams(Object[] value) { this.teams = value; }

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }

    public Object getTeamPlayers() { return teamPlayers; }
    public void setTeamPlayers(Object value) { this.teamPlayers = value; }

    public String getNewPlayerID() { return newPlayerID; }
    public void setNewPlayerID(String value) { this.newPlayerID = value; }
}