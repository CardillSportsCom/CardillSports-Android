package com.cardillsports.stattracker.stats;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;
import com.cardillsports.stattracker.scores.model.GameDay;
import com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import dagger.android.support.AndroidSupportInjection;

import static com.cardillsports.stattracker.stats.GameDayFragment.GAME_ID_KEY;

public class BoxScoreFragment extends Fragment implements BoxScoreViewBinder {

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


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here for versions before M as onAttach(*Context*) did not yet exist
            // This fixes DaggerFragment issue: https://github.com/google/dagger/issues/777
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(*Activity*).
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadBoxScore(gameId);
    }

    @Override
    public void showBoxScore(GameData gameData) {
        Log.d("VITHUSHAN", gameData.toString());

        List<Player> players = new ArrayList<>();
        players.addAll(gameData.getTeamOnePlayers());
        //players.add(Player.create("team", "team", "team"));
        players.addAll(gameData.getTeamTwoPlayers());

        initRecyclerView(tableView, players);
    }

    private void initRecyclerView(TableView tableView, List<Player> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        // Create our custom TableView Adapter
        StatsTableAdapter adapter = new StatsTableAdapter(getActivity()) {
            @Override
            public int getCellItemViewType(int columnPosition) {
                return 1;
            }
        };

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        // Let's set datas of the TableView on the Adapter
        List<StatType> columnHeaderItems = Arrays.asList(StatType.values());

        List<List<Stat>> mCellList = new ArrayList<>();

        for (Player player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.FGM, player.fieldGoalMade()));
            statList.add(new Stat(StatType.MISSES, player.fieldGoalMissed()));
            statList.add(new Stat(StatType.AST, player.assists()));
            statList.add(new Stat(StatType.REB, player.rebounds()));
            statList.add(new Stat(StatType.STL, player.steals()));
            statList.add(new Stat(StatType.BLK, player.blocks()));
            statList.add(new Stat(StatType.TO, player.turnovers()));
            mCellList.add(statList);
        }

        adapter.setAllItems(columnHeaderItems, players, mCellList);
    }

}