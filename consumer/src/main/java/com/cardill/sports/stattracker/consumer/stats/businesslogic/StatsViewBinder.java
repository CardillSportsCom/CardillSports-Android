package com.cardill.sports.stattracker.consumer.stats.businesslogic;

import com.cardill.sports.stattracker.common.data.ConsumerPlayer;

import java.util.List;

public interface StatsViewBinder {
    void showStats(List<ConsumerPlayer> team);
}
