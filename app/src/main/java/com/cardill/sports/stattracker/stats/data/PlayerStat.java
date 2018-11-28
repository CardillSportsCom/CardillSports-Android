package com.cardill.sports.stattracker.stats.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerStat {
    private String id;
    private String player;
    private Team team;
    private String game;
    private String league;
    private long fgm;
    private long fga;
    private long rebounds;
    private long assists;
    private long steals;
    private long blocks;
    private long turnovers;
    private boolean isWin;
    private String dateCreated;
    private long v;

    @JsonProperty("_id")
    public String getID() { return id; }
    @JsonProperty("_id")
    public void setID(String value) { this.id = value; }

    @JsonProperty("player")
    public String getPlayer() { return player; }
    @JsonProperty("player")
    public void setPlayer(String value) { this.player = value; }

    @JsonProperty("team")
    public Team getTeam() { return team; }
    @JsonProperty("team")
    public void setTeam(Team value) { this.team = value; }

    @JsonProperty("game")
    public String getGame() { return game; }
    @JsonProperty("game")
    public void setGame(String value) { this.game = value; }

    @JsonProperty("league")
    public String getLeague() { return league; }
    @JsonProperty("league")
    public void setLeague(String value) { this.league = value; }

    @JsonProperty("FGM")
    public long getFgm() { return fgm; }
    @JsonProperty("FGM")
    public void setFgm(long value) { this.fgm = value; }

    @JsonProperty("FGA")
    public long getFga() { return fga; }
    @JsonProperty("FGA")
    public void setFga(long value) { this.fga = value; }

    @JsonProperty("rebounds")
    public long getRebounds() { return rebounds; }
    @JsonProperty("rebounds")
    public void setRebounds(long value) { this.rebounds = value; }

    @JsonProperty("assists")
    public long getAssists() { return assists; }
    @JsonProperty("assists")
    public void setAssists(long value) { this.assists = value; }

    @JsonProperty("steals")
    public long getSteals() { return steals; }
    @JsonProperty("steals")
    public void setSteals(long value) { this.steals = value; }

    @JsonProperty("blocks")
    public long getBlocks() { return blocks; }
    @JsonProperty("blocks")
    public void setBlocks(long value) { this.blocks = value; }

    @JsonProperty("turnovers")
    public long getTurnovers() { return turnovers; }
    @JsonProperty("turnovers")
    public void setTurnovers(long value) { this.turnovers = value; }

    @JsonProperty("isWin")
    public boolean getIsWin() { return isWin; }
    @JsonProperty("isWin")
    public void setIsWin(boolean value) { this.isWin = value; }

    @JsonProperty("dateCreated")
    public String getDateCreated() { return dateCreated; }
    @JsonProperty("dateCreated")
    public void setDateCreated(String value) { this.dateCreated = value; }

    @JsonProperty("__v")
    public long getV() { return v; }
    @JsonProperty("__v")
    public void setV(long value) { this.v = value; }
}
