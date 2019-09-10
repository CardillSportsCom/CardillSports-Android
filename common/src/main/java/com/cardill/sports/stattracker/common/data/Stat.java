package com.cardill.sports.stattracker.common.data;

import com.evrencoskun.tableview.sort.ISortableModel;

public class Stat implements ISortableModel {

    private String value;
    private final StatType statType;
    private boolean isTeamOne;

    public Stat(StatType statType, String value, boolean isTeamOne) {
        this.value = value;
        this.statType = statType;
        this.isTeamOne = isTeamOne;
    }

    public Stat(StatType statType, int value, boolean isTeamOne) {
        this(statType, String.valueOf(value), isTeamOne);
    }

    public StatType getStatType() {
        return statType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        if (value.contains("%")) {
            int i = value.indexOf("%");
            return Integer.valueOf(value.substring(0, i));
        } else {
            return Integer.valueOf(value);
        }
    }
}
