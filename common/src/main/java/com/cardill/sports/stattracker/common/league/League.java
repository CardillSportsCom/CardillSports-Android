package com.cardill.sports.stattracker.common.league;

import com.fasterxml.jackson.annotation.JsonProperty;

public class League {
    private String _id;
    private String name;
    private String dateCreated;
    private Object type;
    private long v;

    @JsonProperty("_id")
    public String getID() { return _id; }
    @JsonProperty("_id")
    public void setID(String value) { this._id = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("dateCreated")
    public String getDateCreated() { return dateCreated; }
    @JsonProperty("dateCreated")
    public void setDateCreated(String value) { this.dateCreated = value; }

    @JsonProperty("type")
    public Object getType() { return type; }
    @JsonProperty("type")
    public void setType(Object value) { this.type = value; }

    @JsonProperty("__v")
    public long getV() { return v; }
    @JsonProperty("__v")
    public void setV(long value) { this.v = value; }
}
