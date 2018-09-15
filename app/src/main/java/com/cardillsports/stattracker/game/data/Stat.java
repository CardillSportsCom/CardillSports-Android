package com.cardillsports.stattracker.game.data;

import com.evrencoskun.tableview.sort.ISortableModel;

public class Stat implements ISortableModel {

    private int count;
    private final StatType statType;
    private boolean isTeamOne;

    public Stat(StatType statType, int count, boolean isTeamOne) {
        this.count = count;
        this.statType = statType;
        this.isTeamOne = isTeamOne;
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

    public boolean isTeamOne() {
        return isTeamOne;
    }

    @Override
    public String getId() {
        return String.valueOf(hashCode());
    }

    @Override
    public Object getContent() {
        return count;
    }
}
