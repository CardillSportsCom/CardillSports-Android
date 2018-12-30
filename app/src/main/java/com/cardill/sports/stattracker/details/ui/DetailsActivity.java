package com.cardill.sports.stattracker.details.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
import io.reactivex.disposables.Disposable;

public class DetailsActivity extends AppCompatActivity {

    @Inject GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        GameData gameData = gameRepository.getGameStats();

        TableView tableView = findViewById(R.id.team_1_table_view);


        initTableView(tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers(), StatsTableAdapter.EDITABLE);
    }

    private void initTableView(TableView tableView, List<Player> teamOne, List<Player> teamTwo, int viewType) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        StatsTableAdapter adapter = new StatsTableAdapter(this, viewType);

        tableView.setAdapter(adapter);

        List<GameStatType> columnHeaderItems = Arrays.asList(GameStatType.values());
        List<List<Stat>> mCellList = TableUtils.generateTableCellList(teamOne, teamTwo);

        List<GamePlayer> players = new ArrayList<>();

        for (Player player : teamOne) {
            players.add(new GamePlayer(player, true, false));
        }
        for (Player player : teamTwo) {
            players.add(new GamePlayer(player, false, true));
        }

        adapter.setAllItems(columnHeaderItems, players, mCellList);

        int numOfTeamOnePlayers = gameRepository.getGameStats().getTeamOnePlayers().size();

        Disposable disposable = adapter.getChangeEvents()
                .subscribe(detailsChangedEvent -> {
                    int rowPosition = detailsChangedEvent.getRowPosition();
                    Player player;
                    if (rowPosition >= numOfTeamOnePlayers) {
                        rowPosition -= numOfTeamOnePlayers;
                        player = gameRepository.getGameStats().getTeamTwoPlayers().get(rowPosition);
                    } else {
                        player = gameRepository.getGameStats().getTeamOnePlayers().get(rowPosition);
                    }

                    switch (detailsChangedEvent.getColumnPosition()) {
                        case 0:
                            gameRepository.updateStats(player.id(), GameStatType.MAKES, detailsChangedEvent.getNewValue());
                            break;
                        case 1:
                            gameRepository.updateStats(player.id(), GameStatType.MISSES, detailsChangedEvent.getNewValue());
                            break;
                        case 2:
                            gameRepository.updateStats(player.id(), GameStatType.AST, detailsChangedEvent.getNewValue());
                            break;
                        case 3:
                            gameRepository.updateStats(player.id(), GameStatType.REB, detailsChangedEvent.getNewValue());
                            break;
                        case 4:
                            gameRepository.updateStats(player.id(), GameStatType.STL, detailsChangedEvent.getNewValue());
                            break;
                        case 5:
                            gameRepository.updateStats(player.id(), GameStatType.BLK, detailsChangedEvent.getNewValue());
                            break;
                        case 6:
                            gameRepository.updateStats(player.id(), GameStatType.TO, detailsChangedEvent.getNewValue());
                            break;
                        default:
                            throw new UnsupportedOperationException("Invalid column number");
                    }
                });
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

