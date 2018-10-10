package com.cardill.sports.stattracker.boxscore.data;

public class PlayerStat {
    private String id;
    private Player player;
    private Team team;
    private String game;
    private long FGM;
    private long FGA;
    private long rebounds;
    private long assists;
    private long steals;
    private long blocks;
    private long turnovers;
    private String dateCreated;
    private long v;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player value) { this.player = value; }

    public Team getTeam() { return team; }
    public void setTeam(Team value) { this.team = value; }

    public String getGame() { return game; }
    public void setGame(String value) { this.game = value; }

    public long getFGM() { return FGM; }
    public void setFGM(long value) { this.FGM = value; }

    public long getFGA() { return FGA; }
    public void setFGA(long value) { this.FGA = value; }

    public long getRebounds() { return rebounds; }
    public void setRebounds(long value) { this.rebounds = value; }

    public long getAssists() { return assists; }
    public void setAssists(long value) { this.assists = value; }

    public long getSteals() { return steals; }
    public void setSteals(long value) { this.steals = value; }

    public long getBlocks() { return blocks; }
    public void setBlocks(long value) { this.blocks = value; }

    public long getTurnovers() { return turnovers; }
    public void setTurnovers(long value) { this.turnovers = value; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String value) { this.dateCreated = value; }

    public long getV() { return v; }
    public void setV(long value) { this.v = value; }
}
