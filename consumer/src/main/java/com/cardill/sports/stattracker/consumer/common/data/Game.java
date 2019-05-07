package com.cardill.sports.stattracker.consumer.common.data;

import java.io.Serializable;

/**
 * Created by vithushan on 9/12/18.
 */

public class Game implements Serializable {
    private String _id;
    private String teamA;
    private String teamB;
    private String league;
    private String teamAScore;
    private String teamBScore;
    private String dateCreated;
    private long v;
    private Object type;

    public String getID() { return _id; }
    public void setID(String value) { this._id = value; }

    public String getTeamA() { return teamA; }
    public void setTeamA(String value) { this.teamA = value; }

    public String getTeamB() { return teamB; }
    public void setTeamB(String value) { this.teamB = value; }

    public String getLeague() { return league; }
    public void setLeague(String value) { this.league = value; }

    public String getTeamAScore() { return teamAScore; }
    public void setTeamAScore(String value) { this.teamAScore = value; }

    public String getTeamBScore() { return teamBScore; }
    public void setTeamBScore(String value) { this.teamBScore = value; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String value) { this.dateCreated = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }

    public Object getType() { return type; }
    public void setType(Object value) { this.type = value; }
}