package com.cardillsports.stattracker.main.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.main.businesslogic.MainPresenter;
import com.cardillsports.stattracker.main.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.ui.GameActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MainViewBinder {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";
    public static final String GAME_DATA = "game-data-key";

    private MainPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (vithushan) use dagger so you're not creating this twice
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CardillService cardillService = retrofit.create(CardillService.class);

        mPresenter = new MainPresenter(this, cardillService);

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
        List<Player> teamOnePlayers = adapter.getTeamOnePlayers();
        List<Player> teamTwoPlayers = adapter.getTeamTwoPlayers();

        Intent intent = new Intent(this, GameActivity.class);
        GameData gameData = GameData.create(teamOnePlayers, teamTwoPlayers);
        intent.putExtra(GAME_DATA, gameData);
        startActivity(intent);
    }
}
