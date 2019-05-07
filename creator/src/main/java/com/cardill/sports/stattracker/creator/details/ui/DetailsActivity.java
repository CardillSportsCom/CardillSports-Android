package com.cardill.sports.stattracker.creator.details.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cardill.sports.stattracker.creator.R;
import com.cardill.sports.stattracker.creator.details.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.GamePlayer;
import com.cardill.sports.stattracker.common.data.InGameStatType;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.ui.TableUtils;
import com.cardill.sports.stattracker.creator.game.data.GameRepository;
import com.cardill.sports.stattracker.creator.game.ui.PlayerListActivity;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;

import static com.cardill.sports.stattracker.creator.game.ui.PlayerListActivity.SUB_IN_PLAYER_EXTRA_KEY;
import static com.cardill.sports.stattracker.creator.game.ui.PlayerListActivity.SUB_OUT_PLAYER_EXTRA_KEY;

public class DetailsActivity extends AppCompatActivity implements DetailsViewBinder {

    private static final int NEW_PLAYER_REQUEST_CODE = 2;

    @Inject GameRepository gameRepository;
    private TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        GameData gameData = gameRepository.getGameStats();

        tableView = findViewById(R.id.team_1_table_view);


        initTableView(tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers(), StatsTableAdapter.EDITABLE);
    }

    private void initTableView(TableView tableView, List<Player> teamOne, List<Player> teamTwo, int viewType) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        StatsTableAdapter adapter = new StatsTableAdapter(this, viewType);

        tableView.setAdapter(adapter);
        tableView.setTableViewListener(new DetailsTableListener(tableView, this));

        List<InGameStatType> columnHeaderItems = Arrays.asList(InGameStatType.values());
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
                            gameRepository.updateStats(player.id(), InGameStatType.MAKE_ONE_POINT, detailsChangedEvent.getNewValue());
                            break;
                        case 1:
                            gameRepository.updateStats(player.id(), InGameStatType.MAKE_TWO_POINT, detailsChangedEvent.getNewValue());
                            break;
                        case 2:
                            gameRepository.updateStats(player.id(), InGameStatType.MISSES, detailsChangedEvent.getNewValue());
                            break;
                        case 3:
                            gameRepository.updateStats(player.id(), InGameStatType.AST, detailsChangedEvent.getNewValue());
                            break;
                        case 4:
                            gameRepository.updateStats(player.id(), InGameStatType.REB, detailsChangedEvent.getNewValue());
                            break;
                        case 5:
                            gameRepository.updateStats(player.id(), InGameStatType.STL, detailsChangedEvent.getNewValue());
                            break;
                        case 6:
                            gameRepository.updateStats(player.id(), InGameStatType.BLK, detailsChangedEvent.getNewValue());
                            break;
                        case 7:
                            gameRepository.updateStats(player.id(), InGameStatType.TO, detailsChangedEvent.getNewValue());
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

    @Override
    public void showPlayerList(Player subbedOutPlayer) {
        Intent intent = new Intent(this, PlayerListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(SUB_OUT_PLAYER_EXTRA_KEY, subbedOutPlayer);
        intent.putExtras(bundle);
        startActivityForResult(intent, NEW_PLAYER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_PLAYER_REQUEST_CODE) {
                Player subbedOut = data.getParcelableExtra(SUB_OUT_PLAYER_EXTRA_KEY);
                Player subbedIn = data.getParcelableExtra(SUB_IN_PLAYER_EXTRA_KEY);

                String str = "SUB OUT: " + subbedOut.firstName + " :: SUB IN: " + subbedIn.firstName;
                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                performSubstition(subbedOut, subbedIn);
            }
        }
    }

    //TODO move this to presenter class
    private void performSubstition(Player subbedOut, Player subbedIn) {
        List<Player> teamOnePlayers = gameRepository.getGameStats().getTeamOnePlayers();
        List<Player> teamTwoPlayers = gameRepository.getGameStats().getTeamTwoPlayers();

        if (teamOnePlayers.contains(subbedOut)) {
            gameRepository.subOut(subbedOut.id, teamOnePlayers);
            gameRepository.subIn(subbedIn, teamOnePlayers);
        } else if (teamTwoPlayers.contains(subbedOut)) {
            gameRepository.subOut(subbedOut.id, teamTwoPlayers);
            gameRepository.subIn(subbedIn, teamTwoPlayers);
        }

        initTableView(tableView, teamOnePlayers, teamTwoPlayers, StatsTableAdapter.EDITABLE);

    }

}

