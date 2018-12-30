package com.cardill.sports.stattracker.teamselection.data;

import com.cardill.sports.stattracker.common.data.User;

import java.util.List;

public class Team {
    private List<User> users;
    private Object[] substitutes;
    private Object[] injuries;
    private Object[] games;
    private String id;
    private String name;
    private String dateCreated;
    private long v;

    public List<User> getUsers() { return users; }
    public void setUsers(List<User> value) { this.users = value; }

    public Object[] getSubstitutes() { return substitutes; }
    public void setSubstitutes(Object[] value) { this.substitutes = value; }

    public Object[] getInjuries() { return injuries; }
    public void setInjuries(Object[] value) { this.injuries = value; }

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