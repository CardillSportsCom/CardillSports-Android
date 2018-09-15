package com.cardillsports.stattracker.teamselection.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.AddPlayerToLeagueRequestBody;
import com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;
import com.cardillsports.stattracker.teamselection.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.ui.GameActivity;
import com.cardillsports.stattracker.teamselection.data.AddPlayerRequestBody;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import timber.log.Timber;

import static com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter.LEAGUE_ID;

public class TeamSelectionActivity extends AppCompatActivity implements TeamSelectionViewBinder {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";
    public static final String GAME_DATA = "game-data-key";

    private TeamSelectionPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PlayerAdapter adapter;
    @Inject
    CardillService cardillService;
    private View mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        mPresenter = new TeamSelectionPresenter(this, cardillService);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mProgress = findViewById(R.id.progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_next) {
            mPresenter.onTeamsSelected();
            return true;
        }
        if (item.getItemId() == R.id.action_add_player) {
            mPresenter.onAddPlayerRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadPlayers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void loadPlayers(List<Player> players) {
        mProgress.setVisibility(View.GONE);
        adapter = new PlayerAdapter(players);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToGameScreen() {
        finish();

        List<Player> teamOnePlayers = adapter.getTeamOnePlayers();
        List<Player> teamTwoPlayers = adapter.getTeamTwoPlayers();

        Intent intent = new Intent(this, GameActivity.class);
        GameData gameData = new GameData(
                teamOnePlayers,
                teamTwoPlayers,
                true);

        intent.putExtra(GAME_DATA, (Parcelable) gameData);
        startActivity(intent);
    }

    @Override
    public void showPlayerInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Player");
        builder.setMessage("Enter the first name of the player");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String playerName = input.getText().toString();
            Timber.d(playerName);
            mPresenter.addPlayer(playerName);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }
}
