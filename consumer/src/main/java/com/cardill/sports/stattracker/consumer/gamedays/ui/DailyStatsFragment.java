package com.cardill.sports.stattracker.consumer.gamedays.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.common.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.consumer.common.businesslogic.SortableCardillTableListener;
import com.cardill.sports.stattracker.common.data.GamePlayer;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.PlayerStatType;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.consumer.common.data.GameDayStatTotal;
import com.evrencoskun.tableview.TableView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter.NON_EDITABLE;
import static com.cardill.sports.stattracker.consumer.gamedays.ui.GameDayFragment.GAME_DAY_STAT_TOTALS_ID_KEY;

/**
 * Created by vithushan on 9/10/18.
 */

public class DailyStatsFragment extends BaseFragment {

    private TableView tableView;
    private View progress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_stats, container, false);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        GameDayStatTotal[] dailyStats = (GameDayStatTotal[]) getArguments().get(GAME_DAY_STAT_TOTALS_ID_KEY);
        showStats(dailyStats);

        return view;
    }

    public void showStats(GameDayStatTotal[] gameDayStatTotals) {
        progress.setVisibility(View.GONE);

        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<PlayerStatType> columnHeaderItems = Arrays.asList(PlayerStatType.values());
        List<List<Stat>> mCellList = generateTableCellList(gameDayStatTotals);

        List<GamePlayer> gamePlayers = new ArrayList<>();

        for (GameDayStatTotal gameDayStatTotal : gameDayStatTotals) {
            //TODO make all these duplicated Player model classes differentiated
            com.cardill.sports.stattracker.consumer.boxscore.data.Player player = gameDayStatTotal.getPlayer();
            Player convertedPlayer = Player.create(player.getID(), player.getFirstName(), player.getLastName());
            gamePlayers.add(new GamePlayer(convertedPlayer, true, false));
        }
        adapter.setAllItems(columnHeaderItems, gamePlayers, mCellList);

        tableView.setTableViewListener(new SortableCardillTableListener(tableView));

        tableView.setColumnWidth(0, 200);
        tableView.setColumnWidth(1, 200);
        tableView.setColumnWidth(2, 200);
        tableView.setColumnWidth(3, 250);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        tableView.setColumnWidth(10,200);
    }

    private List<List<Stat>> generateTableCellList(GameDayStatTotal[] gameDayStatTotals) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (GameDayStatTotal gameDayStatTotal : gameDayStatTotals) {
            List<Stat> statList = new ArrayList<>(8);
            Map<String, Integer> playerTotalStats = gameDayStatTotal.getPlayerTotalStats();

            Integer fgm = playerTotalStats.get("FGM");
            Integer fga = playerTotalStats.get("FGA");

            double fg = 0;
            if (fgm != 0) {
                fg = fgm / (double) fga;
            }
            NumberFormat percentInstance = NumberFormat.getPercentInstance();

            statList.add(new Stat(PlayerStatType.WINS, playerTotalStats.get("gamesWon"), true));
            statList.add(new Stat(PlayerStatType.GP, playerTotalStats.get("gamesPlayed"), true));
            statList.add(new Stat(PlayerStatType.FG_PERCENT, percentInstance.format(fg), true));
           // statList.add(new Stat(PlayerStatType.POINTS, playerTotalStats.get("points"), true));
            statList.add(new Stat(PlayerStatType.THREES, playerTotalStats.get("threePointersMade"), true));
            statList.add(new Stat(PlayerStatType.AST, playerTotalStats.get("assists"), true));
            statList.add(new Stat(PlayerStatType.REB, playerTotalStats.get("rebounds"), true));
            statList.add(new Stat(PlayerStatType.STL, playerTotalStats.get("steals"), true));
            statList.add(new Stat(PlayerStatType.BLK, playerTotalStats.get("blocks"), true));
            statList.add(new Stat(PlayerStatType.TO, playerTotalStats.get("turnovers"), true));
            cellList.add(statList);
        }

        return cellList;
    }
}
