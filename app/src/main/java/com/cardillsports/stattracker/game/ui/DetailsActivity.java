package com.cardillsports.stattracker.game.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.MockData;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.details.businesslogic.DetailsChangedEvent;
import com.cardillsports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.functions.Consumer;

public class DetailsActivity extends AppCompatActivity {

    @Inject GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        GameData gameData = gameRepository.getGameStats();
        //TODO paginate this table
        List<Player> players = new ArrayList<>();
        players.addAll(gameData.teamOnePlayers());
        //players.add(Player.create("team", "team", "team"));
        players.addAll(gameData.teamTwoPlayers());

        TableView tableView = findViewById(R.id.team_1_table_view);


        initRecyclerView(tableView, players);
    }

    private void initRecyclerView(TableView tableView, List<Player> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        // Create our custom TableView Adapter
        StatsTableAdapter adapter = new StatsTableAdapter(this) {
            @Override
            public int getCellItemViewType(int columnPosition) {
                return 0;
            }
        };

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        // Let's set datas of the TableView on the Adapter
        List<StatType> columnHeaderItems = Arrays.asList(StatType.values());

        List<List<Stat>> mCellList = new ArrayList<>();

        for (Player player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(StatType.FIELD_GOAL_MADE, player.fieldGoalMade()));
            statList.add(new Stat(StatType.FIELD_GOAL_MISSED, player.fieldGoalMissed()));
            statList.add(new Stat(StatType.ASSISTS, player.assists()));
            statList.add(new Stat(StatType.REBOUNDS, player.rebounds()));
            statList.add(new Stat(StatType.STEALS, player.steals()));
            statList.add(new Stat(StatType.BLOCKS, player.blocks()));
            statList.add(new Stat(StatType.TURNOVERS, player.turnovers()));
            mCellList.add(statList);
        }

        adapter.setAllItems(columnHeaderItems, players, mCellList);

        int numOfTeamOnePlayers = gameRepository.getGameStats().teamOnePlayers().size();

        adapter.getChangeEvents()
                .subscribe(new Consumer<DetailsChangedEvent>() {
                    @Override
                    public void accept(DetailsChangedEvent detailsChangedEvent) throws Exception {
                        int rowPosition = detailsChangedEvent.getRowPosition();
                        Player player;
                        if (rowPosition >= numOfTeamOnePlayers) {
                            rowPosition -= numOfTeamOnePlayers;
                            player = gameRepository.getGameStats().teamTwoPlayers().get(rowPosition);
                        } else {
                            player = gameRepository.getGameStats().teamOnePlayers().get(rowPosition);
                        }

                        switch (detailsChangedEvent.getColumnPosition()) {
                            case 0:
                                gameRepository.updateStats(player.id(), StatType.FIELD_GOAL_MADE, detailsChangedEvent.getNewValue());
                                break;
                            case 1:
                                gameRepository.updateStats(player.id(), StatType.FIELD_GOAL_MISSED, detailsChangedEvent.getNewValue());
                                break;
                            case 2:
                                gameRepository.updateStats(player.id(), StatType.ASSISTS, detailsChangedEvent.getNewValue());
                                break;
                            case 3:
                                gameRepository.updateStats(player.id(), StatType.REBOUNDS, detailsChangedEvent.getNewValue());
                                break;
                            case 4:
                                gameRepository.updateStats(player.id(), StatType.STEALS, detailsChangedEvent.getNewValue());
                                break;
                            case 5:
                                gameRepository.updateStats(player.id(), StatType.BLOCKS, detailsChangedEvent.getNewValue());
                                break;
                            case 6:
                                gameRepository.updateStats(player.id(), StatType.TURNOVERS, detailsChangedEvent.getNewValue());
                                break;
                            default:
                                throw new UnsupportedOperationException("Invalid column number");
                        }
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

