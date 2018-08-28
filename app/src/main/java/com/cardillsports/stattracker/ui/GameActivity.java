package com.cardillsports.stattracker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.businesslogic.GamePlayerAdapter;
import com.cardillsports.stattracker.businesslogic.GamePresenter;
import com.cardillsports.stattracker.data.CardillService;
import com.cardillsports.stattracker.data.GameData;
import com.cardillsports.stattracker.data.GameRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cardillsports.stattracker.ui.MainActivity.GAME_DATA;

public class GameActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";

    private GamePresenter mPresenter;
    private GamePlayerAdapter teamOneAdapter;
    private GamePlayerAdapter teamTwoAdapter;
    private GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameData gameData = getIntent().getParcelableExtra(GAME_DATA);

        gameRepository = new GameRepository(gameData);

        RecyclerView teamOneRecyclerView = findViewById(R.id.team_1_recycler_view);
        teamOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamOneAdapter = new GamePlayerAdapter(gameData.teamOnePlayers(), gameRepository);
        teamOneRecyclerView.setAdapter(teamOneAdapter);

        RecyclerView teamTwoRecyclerView = findViewById(R.id.team_2_recycler_view);
        teamTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamTwoAdapter = new GamePlayerAdapter(gameData.teamTwoPlayers(), gameRepository);
        teamTwoRecyclerView.setAdapter(teamTwoAdapter);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CardillService cardillService = retrofit.create(CardillService.class);

        mPresenter = new GamePresenter(gameRepository, cardillService);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {

            //TODO (vithushan) save stats for real

            mPresenter.submitGameStats();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
