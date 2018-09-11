package com.cardillsports.stattracker.teamselection.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;
import com.cardillsports.stattracker.teamselection.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.ui.GameActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TeamSelectionActivity extends AppCompatActivity implements TeamSelectionViewBinder {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";
    public static final String GAME_DATA = "game-data-key";

    private TeamSelectionPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PlayerAdapter adapter;
    @Inject CardillService cardillService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new TeamSelectionPresenter(this, cardillService);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void loadPlayers(List<Player> players) {
        adapter = new PlayerAdapter(players);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void navigateToGameScreen() {
        finish();

        List<Player> teamOnePlayers = adapter.getTeamOnePlayers();
        List<Player> teamTwoPlayers = adapter.getTeamTwoPlayers();

        Intent intent = new Intent(this, GameActivity.class);
        GameData gameData = GameData.create(teamOnePlayers, teamTwoPlayers);
        intent.putExtra(GAME_DATA, gameData);
        startActivity(intent);
    }
}
