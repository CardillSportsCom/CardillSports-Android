package com.cardill.sports.stattracker.consumer.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.common.businesslogic.ConsumerPlayerStatsTableAdapter;
import com.cardill.sports.stattracker.common.data.ConsumerGamePlayer;
import com.cardill.sports.stattracker.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.common.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.common.businesslogic.SortableCardillTableListener;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.GamePlayer;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.PlayerStatType;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.consumer.network.CardillService;
import com.cardill.sports.stattracker.consumer.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.consumer.stats.businesslogic.StatsViewBinder;
import com.evrencoskun.tableview.TableView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter.NON_EDITABLE;

/**
 * Created by vithushan on 9/10/18.
 */

public class StatsFragment extends BaseFragment implements StatsViewBinder {

    private TableView tableView;
    private View progress;
    private StatsPresenter mPresenter;

    @Inject
    CardillService cardillService;
    @Inject LeagueRepository leagueRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        mPresenter = new StatsPresenter(this, cardillService, leagueRepo);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadLeagueTotals();
    }

    @Override
    public void showStats(List<ConsumerPlayer> team) {
        progress.setVisibility(View.GONE);

        initTableView(tableView, team);
    }

    private void initTableView(TableView tableView, List<ConsumerPlayer> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        ConsumerPlayerStatsTableAdapter adapter = new ConsumerPlayerStatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<PlayerStatType> columnHeaderItems = Arrays.asList(PlayerStatType.values());
        List<List<Stat>> mCellList = generateTableCellList(players);

        List<ConsumerGamePlayer> gamePlayers = new ArrayList<>();

        for (ConsumerPlayer player : players) {
            gamePlayers.add(new ConsumerGamePlayer(player, true, false));
        }
        adapter.setAllItems(columnHeaderItems, gamePlayers, mCellList);

        tableView.setTableViewListener(new SortableCardillTableListener(tableView));

        tableView.setColumnWidth(0, 200);
        tableView.setColumnWidth(1, 200);
        tableView.setColumnWidth(2, 400);
        tableView.setColumnWidth(3, 200);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        tableView.setColumnWidth(10,200);
    }

    private List<List<Stat>> generateTableCellList(List<ConsumerPlayer> players) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (ConsumerPlayer player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(PlayerStatType.WINS, player.wins(), true));
            statList.add(new Stat(PlayerStatType.GP, player.gamesPlayed(), true));

            double fg = 0;
            if (player.fieldGoalMade() != 0) {
                fg = player.fieldGoalMade() / (double) player.fieldGoalMissed();
            }
            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            String fieldGoalString = percentInstance.format(fg) +
                    " (" + player.fieldGoalMade() + " / " + player.fieldGoalMissed() + ") ";
            statList.add(new Stat(PlayerStatType.FG_PERCENT, fieldGoalString, true));

            statList.add(new Stat(PlayerStatType.POINTS, player.points(), true));
            statList.add(new Stat(PlayerStatType.THREES, player.getThreePointersMade(), true));
            statList.add(new Stat(PlayerStatType.AST, player.assists(), true));
            statList.add(new Stat(PlayerStatType.REB, player.rebounds(), true));
            statList.add(new Stat(PlayerStatType.STL, player.steals(), true));
            statList.add(new Stat(PlayerStatType.BLK, player.blocks(), true));
            statList.add(new Stat(PlayerStatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        return cellList;
    }
}
