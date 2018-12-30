package com.cardill.sports.stattracker.common.ui;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.data.GameStatType;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    public static List<List<Stat>> generateTableCellList(List<Player> teamOne, List<Player> teamTwo) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : teamOne) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(GameStatType.MAKES, player.fieldGoalMade(), true));
            statList.add(new Stat(GameStatType.MISSES, player.fieldGoalMissed(), true));
            statList.add(new Stat(GameStatType.AST, player.assists(), true));
            statList.add(new Stat(GameStatType.REB, player.rebounds(), true));
            statList.add(new Stat(GameStatType.STL, player.steals(), true));
            statList.add(new Stat(GameStatType.BLK, player.blocks(), true));
            statList.add(new Stat(GameStatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        for (Player player : teamTwo) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(GameStatType.MAKES, player.fieldGoalMade(), false));
            statList.add(new Stat(GameStatType.MISSES, player.fieldGoalMissed(), false));
            statList.add(new Stat(GameStatType.AST, player.assists(), false));
            statList.add(new Stat(GameStatType.REB, player.rebounds(), false));
            statList.add(new Stat(GameStatType.STL, player.steals(), false));
            statList.add(new Stat(GameStatType.BLK, player.blocks(), false));
            statList.add(new Stat(GameStatType.TO, player.turnovers(), false));
            cellList.add(statList);
        }

        return cellList;
    }
}
