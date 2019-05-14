package com.cardill.sports.stattracker.common.ui;

import com.cardill.sports.stattracker.common.data.InGameStatType;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.Stat;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    public static List<List<Stat>> generateTableCellList(List<Player> teamOne, List<Player> teamTwo) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : teamOne) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(InGameStatType.MAKE_ONE_POINT, player.getOnePointFieldGoalMade(), true));
            statList.add(new Stat(InGameStatType.MAKE_TWO_POINT, player.getTwoPointFieldGoalMade(), true));
            statList.add(new Stat(InGameStatType.MISSES, player.fieldGoalMissed(), true));
            statList.add(new Stat(InGameStatType.AST, player.assists(), true));
            statList.add(new Stat(InGameStatType.REB, player.rebounds(), true));
            statList.add(new Stat(InGameStatType.STL, player.steals(), true));
            statList.add(new Stat(InGameStatType.BLK, player.blocks(), true));
            statList.add(new Stat(InGameStatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        for (Player player : teamTwo) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(InGameStatType.MAKE_ONE_POINT, player.getOnePointFieldGoalMade(), false));
            statList.add(new Stat(InGameStatType.MAKE_TWO_POINT, player.getTwoPointFieldGoalMade(), false));
            statList.add(new Stat(InGameStatType.MISSES, player.fieldGoalMissed(), false));
            statList.add(new Stat(InGameStatType.AST, player.assists(), false));
            statList.add(new Stat(InGameStatType.REB, player.rebounds(), false));
            statList.add(new Stat(InGameStatType.STL, player.steals(), false));
            statList.add(new Stat(InGameStatType.BLK, player.blocks(), false));
            statList.add(new Stat(InGameStatType.TO, player.turnovers(), false));
            cellList.add(statList);
        }

        return cellList;
    }
}
