package com.cardillsports.stattracker.stats.data;

public class PlayerTotalStats {
    private long FGM;
    private long FGA;
    private long rebounds;
    private long assists;
    private long steals;
    private long blocks;
    private long turnovers;
    private long gamesPlayed;

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

    public long getGamesPlayed() { return gamesPlayed; }
    public void setGamesPlayed(long value) { this.gamesPlayed = value; }
}
