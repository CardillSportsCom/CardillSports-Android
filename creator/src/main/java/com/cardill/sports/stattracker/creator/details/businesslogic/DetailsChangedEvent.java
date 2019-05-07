package com.cardill.sports.stattracker.creator.details.businesslogic;

/**
 * Created by vithushan on 9/11/18.
 */

public class DetailsChangedEvent {
    private final int columnPosition;
    private final int rowPosition;
    private final int newValue;

    public DetailsChangedEvent(int columnPosition, int rowPosition, int newValue) {
        this.columnPosition = columnPosition;
        this.rowPosition = rowPosition;
        this.newValue = newValue;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getNewValue() {
        return newValue;
    }
}
