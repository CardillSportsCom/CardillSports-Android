package com.cardill.sports.stattracker.consumer.common.data;

import com.cardill.sports.stattracker.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.common.data.InGameStatType;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.consumer.profile.data.HistoricalStatType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsumerTableUtils {
    public static List<List<Stat>> generateConsumerTableCellList(List<ConsumerPlayer> teamOne, List<ConsumerPlayer> teamTwo, NumberFormat numberFormat) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (ConsumerPlayer player : teamOne) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(HistoricalStatType.FG_PERCENT, player.getFieldGoaldPercentage(numberFormat), true));
            statList.add(new Stat(HistoricalStatType.POINTS, player.points(), true));
            statList.add(new Stat(HistoricalStatType.THREES, player.getThreePointersMade(), true));
            statList.add(new Stat(HistoricalStatType.AST, player.assists(), true));
            statList.add(new Stat(HistoricalStatType.REB, player.rebounds(), true));
            statList.add(new Stat(HistoricalStatType.STL, player.steals(), true));
            statList.add(new Stat(HistoricalStatType.BLK, player.blocks(), true));
            statList.add(new Stat(HistoricalStatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        for (ConsumerPlayer player : teamTwo) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(HistoricalStatType.FG_PERCENT, player.getFieldGoaldPercentage(numberFormat), false));
            statList.add(new Stat(HistoricalStatType.POINTS, player.getThreePointersMade(), false));
            statList.add(new Stat(HistoricalStatType.THREES, player.fieldGoalMissed(), false));
            statList.add(new Stat(HistoricalStatType.AST, player.assists(), false));
            statList.add(new Stat(HistoricalStatType.REB, player.rebounds(), false));
            statList.add(new Stat(HistoricalStatType.STL, player.steals(), false));
            statList.add(new Stat(HistoricalStatType.BLK, player.blocks(), false));
            statList.add(new Stat(HistoricalStatType.TO, player.turnovers(), false));
            cellList.add(statList);
        }

        return cellList;
    }
}
