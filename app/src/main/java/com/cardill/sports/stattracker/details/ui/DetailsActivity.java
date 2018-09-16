package com.cardill.sports.stattracker.details.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.TableUtils;
import com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.GameRepository;
import com.cardill.sports.stattracker.game.data.Stat;
import com.cardill.sports.stattracker.game.data.StatType;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;
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

        GameData gameData = gameRepository.getGameStats();

        TableView tableView = findViewById(R.id.team_1_table_view);


        initTableView(tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers(), StatsTableAdapter.EDITABLE);
    }

    private void initTableView(TableView tableView, List<Player> teamOne, List<Player> teamTwo, int viewType) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        StatsTableAdapter adapter = new StatsTableAdapter(this, viewType);

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
                            gameRepository.updateStats(player.id(), StatType.FGM, detailsChangedEvent.getNewValue());
                            break;
                        case 1:
                            gameRepository.updateStats(player.id(), StatType.MISSES, detailsChangedEvent.getNewValue());
                            break;
                        case 2:
                            gameRepository.updateStats(player.id(), StatType.AST, detailsChangedEvent.getNewValue());
                            break;
                        case 3:
                            gameRepository.updateStats(player.id(), StatType.REB, detailsChangedEvent.getNewValue());
                            break;
                        case 4:
                            gameRepository.updateStats(player.id(), StatType.STL, detailsChangedEvent.getNewValue());
                            break;
                        case 5:
                            gameRepository.updateStats(player.id(), StatType.BLK, detailsChangedEvent.getNewValue());
                            break;
                        case 6:
                            gameRepository.updateStats(player.id(), StatType.TO, detailsChangedEvent.getNewValue());
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

