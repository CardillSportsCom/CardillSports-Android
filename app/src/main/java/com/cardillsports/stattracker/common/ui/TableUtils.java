package com.cardillsports.stattracker.common.ui;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;

import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    public static List<List<Stat>> generateTableCellList(List<Player> playerList) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : playerList) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.FGM, player.fieldGoalMade()));
            statList.add(new Stat(StatType.MISSES, player.fieldGoalMissed()));
            statList.add(new Stat(StatType.AST, player.assists()));
            statList.add(new Stat(StatType.REB, player.rebounds()));
            statList.add(new Stat(StatType.STL, player.steals()));
            statList.add(new Stat(StatType.BLK, player.blocks()));
            statList.add(new Stat(StatType.TO, player.turnovers()));
            cellList.add(statList);
        }

        return cellList;
    }
}
