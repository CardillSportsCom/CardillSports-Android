package com.cardillsports.stattracker.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.businesslogic.CardillPresenter;
import com.cardillsports.stattracker.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.data.GameData;
import com.cardillsports.stattracker.data.Player;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CardillViewBinder {

    private static final String GAME_DATA = "game-data-key";
    private CardillPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new CardillPresenter(this);

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
            List<Player> teamOnePlayers = adapter.getTeamOnePlayers();
            List<Player> teamTwoPlayers = adapter.getTeamTwoPlayers();

            Intent intent = new Intent(this, GameActivity.class);
            GameData gameData = GameData.create(teamOnePlayers, teamTwoPlayers);
            intent.putExtra(GAME_DATA, gameData);
            startActivity(intent);
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
}
