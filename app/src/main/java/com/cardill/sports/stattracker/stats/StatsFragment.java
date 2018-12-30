package com.cardill.sports.stattracker.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.businesslogic.SortableCardillTableListener;
import com.cardill.sports.stattracker.common.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.common.data.PlayerStatType;
import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.stats.businesslogic.StatsViewBinder;
import com.cardill.sports.stattracker.common.data.GamePlayer;
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
    public void showStats(GameData gameData) {
        progress.setVisibility(View.GONE);

        List<Player> players = new ArrayList<>();
        players.addAll(gameData.getTeamOnePlayers());
        players.addAll(gameData.getTeamTwoPlayers());

        initTableView(tableView, players);
    }

    private void initTableView(TableView tableView, List<Player> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<PlayerStatType> columnHeaderItems = Arrays.asList(PlayerStatType.values());
        List<List<Stat>> mCellList = generateTableCellList(players);

        List<GamePlayer> gamePlayers = new ArrayList<>();

        for (Player player : players) {
            gamePlayers.add(new GamePlayer(player, true, false));
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

    private List<List<Stat>> generateTableCellList(List<Player> players) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (Player player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(PlayerStatType.WINS, player.wins(), true));
            statList.add(new Stat(PlayerStatType.GP, player.gamesPlayed(), true));
            statList.add(new Stat(PlayerStatType.FGM, player.fieldGoalMade(), true));
            statList.add(new Stat(PlayerStatType.FGA, player.fieldGoalMissed() + player.fieldGoalMade(), true));

            double fg = 0;
            if (player.fieldGoalMade() != 0) {
                fg = player.fieldGoalMade() / (double) (player.fieldGoalMissed() + player.fieldGoalMade());
            }
            NumberFormat percentInstance = NumberFormat.getPercentInstance();

            statList.add(new Stat(PlayerStatType.FG, percentInstance.format(fg), true));
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
