package com.cardill.sports.stattracker.stats.data;

/**
 * Another player model class, can we consolidate them?
 */
public class Player {
    private Object[] teams;
    private String _id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUri;
    private String password;
    private String dateCreated;
    private Object[] leagues;
    private long v;
    private Object teamPlayers;
    private String playerID;

    public Object[] getTeams() { return teams; }
    public void setTeams(Object[] value) { this.teams = value; }

    public String getID() { return _id; }
    public void setID(String value) { this._id = value; }

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

    public Object[] getLeagues() { return leagues; }
    public void setLeagues(Object[] value) { this.leagues = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }

    public Object getTeamPlayers() { return teamPlayers; }
    public void setTeamPlayers(Object value) { this.teamPlayers = value; }

    public String getPlayerID() { return playerID; }
    public void setPlayerID(String value) { this.playerID = value; }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}