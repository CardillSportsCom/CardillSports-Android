package com.cardillsports.stattracker.scores.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.common.ui.BaseFragment;
import com.cardillsports.stattracker.common.ui.TableUtils;
import com.cardillsports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;
import com.cardillsports.stattracker.scores.businesslogic.BoxScorePresenter;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.cardillsports.stattracker.details.businesslogic.StatsTableAdapter.NON_EDITABLE;
import static com.cardillsports.stattracker.scores.ui.GameDayFragment.GAME_ID_KEY;

public class BoxScoreFragment extends BaseFragment implements BoxScoreViewBinder {

    private BoxScorePresenter mPresenter;
    private String gameId;
    @Inject
    CardillService cardillService;
    private TableView tableView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_box_score, container, false);

        gameId = (String) getArguments().get(GAME_ID_KEY);

        mPresenter = new BoxScorePresenter(this, cardillService);

        tableView = view.findViewById(R.id.team_1_table_view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadBoxScore(gameId);
    }

    @Override
    public void showBoxScore(GameData gameData) {
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
        List<List<Stat>> mCellList = TableUtils.generateTableCellList(players);

        adapter.setAllItems(columnHeaderItems, players, mCellList);
    }

}