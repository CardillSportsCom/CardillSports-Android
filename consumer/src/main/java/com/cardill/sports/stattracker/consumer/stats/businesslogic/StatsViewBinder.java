package com.cardill.sports.stattracker.consumer.stats.businesslogic;

import com.cardill.sports.stattracker.common.data.GameData;

public interface StatsViewBinder {
    void showStats(GameData gameData);
}
