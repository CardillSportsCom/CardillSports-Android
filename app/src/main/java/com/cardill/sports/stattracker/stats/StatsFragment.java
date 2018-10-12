package com.cardill.sports.stattracker.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.SortableCardillTableListener;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.Stat;
import com.cardill.sports.stattracker.game.data.StatType;
import com.cardill.sports.stattracker.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.stats.businesslogic.StatsViewBinder;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter.NON_EDITABLE;

/**
 * Created by vithushan on 9/10/18.
 */

public class StatsFragment extends BaseFragment implements StatsViewBinder {

    private TableView tableView;
    private View progress;
    private StatsPresenter mPresenter;

    @Inject
    CardillService cardillService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        mPresenter = new StatsPresenter(this, cardillService);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadStatTotals();
    }

    @Override
    public void showStats(GameData gameData) {
        progress.setVisibility(View.GONE);

        List<Player> players = new ArrayList<>();
        players.addAll(gameData.getTeamOnePlayers());
        players.addAll(gameData.getTeamTwoPlayers());

        initTableView(tableView, players);
    }

    private void initTableView(TableView tableView, List<Player> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        StatsTableAdapter adapter = new StatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<StatType> columnHeaderItems = Arrays.asList(StatType.values());
        List<List<Stat>> mCellList = generateTableCellList(players);

        List<NewGamePlayer> newGamePlayers = new ArrayList<>();

        for (Player player : players) {
            newGamePlayers.add(new NewGamePlayer(player, true, false));
        }
        adapter.setAllItems(columnHeaderItems, newGamePlayers, mCellList);

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

    private List<List<Stat>> generateTableCellList(List<Player> players) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.WINS, player.wins(), true));
            statList.add(new Stat(StatType.GP, player.gamesPlayed(), true));
            statList.add(new Stat(StatType.FGM, player.fieldGoalMade(), true));
            statList.add(new Stat(StatType.MISSES, player.fieldGoalMissed(), true));
            statList.add(new Stat(StatType.AST, player.assists(), true));
            statList.add(new Stat(StatType.REB, player.rebounds(), true));
            statList.add(new Stat(StatType.STL, player.steals(), true));
            statList.add(new Stat(StatType.BLK, player.blocks(), true));
            statList.add(new Stat(StatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        return cellList;
    }
}
