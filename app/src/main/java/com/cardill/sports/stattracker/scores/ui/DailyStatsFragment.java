package com.cardill.sports.stattracker.scores.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.CardillTableListener;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.Stat;
import com.cardill.sports.stattracker.game.data.StatType;
import com.cardill.sports.stattracker.scores.model.GameDayStatTotal;
import com.cardill.sports.stattracker.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.stats.businesslogic.StatsViewBinder;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter.NON_EDITABLE;
import static com.cardill.sports.stattracker.scores.ui.GameDayFragment.GAME_DAY_STAT_TOTALS_ID_KEY;
import static com.cardill.sports.stattracker.scores.ui.GameDayFragment.GAME_ID_KEY;

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
        StatsTableAdapter adapter = new StatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<StatType> columnHeaderItems = Arrays.asList(StatType.values());
        List<List<Stat>> mCellList = generateTableCellList(gameDayStatTotals);

        List<NewGamePlayer> newGamePlayers = new ArrayList<>();

        for (GameDayStatTotal gameDayStatTotal : gameDayStatTotals) {
            //TODO make all these duplicated Player model classes differentiated
            com.cardill.sports.stattracker.scores.model.boxscore.Player player = gameDayStatTotal.getPlayer();
            Player convertedPlayer = Player.create(player.getID(), player.getFirstName(), player.getLastName());
            newGamePlayers.add(new NewGamePlayer(convertedPlayer, true, false));
        }
        adapter.setAllItems(columnHeaderItems, newGamePlayers, mCellList);

        tableView.setTableViewListener(new CardillTableListener(tableView));

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

            statList.add(new Stat(StatType.WINS, playerTotalStats.get("gamesWon"), true));
            statList.add(new Stat(StatType.GP, playerTotalStats.get("gamesPlayed"), true));
            statList.add(new Stat(StatType.FGM, playerTotalStats.get("FGM"), true));
            statList.add(new Stat(StatType.MISSES, playerTotalStats.get("FGA") - playerTotalStats.get("FGM"), true));
            statList.add(new Stat(StatType.AST, playerTotalStats.get("assists"), true));
            statList.add(new Stat(StatType.REB, playerTotalStats.get("rebounds"), true));
            statList.add(new Stat(StatType.STL, playerTotalStats.get("steals"), true));
            statList.add(new Stat(StatType.BLK, playerTotalStats.get("blocks"), true));
            statList.add(new Stat(StatType.TO, playerTotalStats.get("turnovers"), true));
            cellList.add(statList);
        }

        return cellList;
    }
}
