package com.cardillsports.stattracker.scores.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.cardillsports.stattracker.teamselection.data.NewGamePlayer;
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
    private View progress;
    private TextView score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_box_score, container, false);

        gameId = (String) getArguments().get(GAME_ID_KEY);

        mPresenter = new BoxScorePresenter(this, cardillService);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        score = view.findViewById(R.id.score_textview);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadBoxScore(gameId);
    }

    @Override
    public void showBoxScore(GameData gameData) {
        progress.setVisibility(View.GONE);

        int team1 = 0;
        for (Player player : gameData.getTeamOnePlayers()) {
            team1 += player.fieldGoalMade();
        }

        int team2 = 0;
        for (Player player : gameData.getTeamTwoPlayers()) {
            team2 += player.fieldGoalMade();
        }

        String scoreText = team1 + " - " + team2;

        score.setText(scoreText);


        initTableView(tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers());


    }

    private void initTableView(TableView tableView, List<Player> teamOne, List<Player> teamTwo) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        StatsTableAdapter adapter = new StatsTableAdapter(getActivity(), NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<StatType> columnHeaderItems = Arrays.asList(StatType.values()).subList(2,9);
        List<List<Stat>> mCellList = TableUtils.generateTableCellList(teamOne, teamTwo);

        List<NewGamePlayer> players = new ArrayList<>();

        for (Player player : teamOne) {
            players.add(new NewGamePlayer(player, true, false));
        }
        for (Player player : teamTwo) {
            players.add(new NewGamePlayer(player, false, true));
        }

        adapter.setAllItems(columnHeaderItems, players, mCellList);

        tableView.setColumnWidth(0, 200);
        tableView.setColumnWidth(1, 250);
        tableView.setColumnWidth(2, 200);
        tableView.setColumnWidth(3, 200);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        tableView.setColumnWidth(10,200);
    }

}