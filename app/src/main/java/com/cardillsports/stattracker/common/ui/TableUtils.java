package com.cardillsports.stattracker.common.ui;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    public static List<List<Stat>> generateTableCellList(List<Player> teamOne, List<Player> teamTwo) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : teamOne) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.FGM, player.fieldGoalMade(), true));
            statList.add(new Stat(StatType.MISSES, player.fieldGoalMissed(), true));
            statList.add(new Stat(StatType.AST, player.assists(), true));
            statList.add(new Stat(StatType.REB, player.rebounds(), true));
            statList.add(new Stat(StatType.STL, player.steals(), true));
            statList.add(new Stat(StatType.BLK, player.blocks(), true));
            statList.add(new Stat(StatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        for (Player player : teamTwo) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.FGM, player.fieldGoalMade(), false));
            statList.add(new Stat(StatType.MISSES, player.fieldGoalMissed(), false));
            statList.add(new Stat(StatType.AST, player.assists(), false));
            statList.add(new Stat(StatType.REB, player.rebounds(), false));
            statList.add(new Stat(StatType.STL, player.steals(), false));
            statList.add(new Stat(StatType.BLK, player.blocks(), false));
            statList.add(new Stat(StatType.TO, player.turnovers(), false));
            cellList.add(statList);
        }

        return cellList;
    }
}
