package com.cardill.sports.stattracker.game.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.TableUtils;
import com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.game.data.GameRepository;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.data.GameStatType;
import com.cardill.sports.stattracker.common.data.GamePlayer;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter.NON_EDITABLE;

public class GameRecapActivity extends AppCompatActivity {

    @Inject GameRepository gameRepository;
    private TextView scoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_score);

        GameData gameData = gameRepository.getGameStats();

        TableView tableView = findViewById(R.id.team_1_table_view);

        scoreView = findViewById(R.id.score_textview);

        initTableView(tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers());
    }

    private void initTableView(TableView tableView, List<Player> team1, List<Player> team2) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);

        StatsTableAdapter adapter = new StatsTableAdapter(this, NON_EDITABLE);
        tableView.setAdapter(adapter);

        List<GameStatType> columnHeaderItems = Arrays.asList(GameStatType.values());

        List<List<Stat>> cellList = TableUtils.generateTableCellList(team1,team2);

        List<GamePlayer> players = new ArrayList<>();

        for (Player player : team1) {
            players.add(new GamePlayer(player, true, false));
        }
        for (Player player : team2) {
            players.add(new GamePlayer(player, false, true));
        }
        adapter.setAllItems(columnHeaderItems, players, cellList);

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

        int team1Score = 0;
        int team2Score = 0;
        for (Player player : team1) {
            team1Score += player.fieldGoalMade;
        }
        for (Player player : team2) {
            team2Score += player.fieldGoalMade;
        }

        String scoreText = team1Score + " - " + team2Score;
        scoreView.setText(scoreText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_done) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

