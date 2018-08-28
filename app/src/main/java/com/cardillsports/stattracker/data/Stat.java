package com.cardillsports.stattracker.data;

public class Stat {

    private int count;
    private final StatType statType;

    Stat(StatType statType) {
        this.statType = statType;
        count = 0;
    }

    public Stat(StatType statType, int count) {
        this.count = count;
        this.statType = statType;
    }

    public StatType getStatType() {
        return statType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
