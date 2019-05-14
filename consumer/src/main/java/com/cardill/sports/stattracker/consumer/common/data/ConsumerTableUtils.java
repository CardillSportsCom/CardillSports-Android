package com.cardill.sports.stattracker.consumer.common.data;

import com.cardill.sports.stattracker.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.consumer.profile.data.HistoricalStatType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ConsumerTableUtils {
    public static List<List<Stat>> generateConsumerTableCellList(List<ConsumerPlayer> teamOne, List<ConsumerPlayer> teamTwo, NumberFormat numberFormat) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (ConsumerPlayer player : teamOne) {
            cellList.add(createStatList(player, true));
        }

        for (ConsumerPlayer player : teamTwo) {
            cellList.add(createStatList(player, false));
        }

        return cellList;
    }

    private static List<Stat> createStatList(ConsumerPlayer player, boolean isTeamOne) {
        List<Stat> statList = new ArrayList<>(8);

        double fg = 0;
        if (player.fieldGoalMade() != 0) {
            fg = player.fieldGoalMade() / (double) (player.fieldGoalMissed());
        }
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        String fieldGoalString = percentInstance.format(fg) +
                " (" + player.fieldGoalMade() + " / " + player.fieldGoalMissed() + ") ";
        statList.add(new Stat(HistoricalStatType.FG_PERCENT, fieldGoalString, isTeamOne));

        statList.add(new Stat(HistoricalStatType.POINTS, player.points(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.THREES, player.getThreePointersMade(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.AST, player.assists(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.REB, player.rebounds(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.STL, player.steals(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.BLK, player.blocks(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.TO, player.turnovers(), isTeamOne));

        return statList;
    }
}
