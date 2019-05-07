package com.cardill.sports.stattracker.consumer.profile.data;

import com.cardill.sports.stattracker.common.data.StatType;

/**
 * The stats we display on the player profiles
 */
public enum HistoricalStatType implements StatType {
    FG_PERCENT,
    POINTS,
    THREES,
    AST,
    REB,
    STL,
    BLK,
    TO
}
