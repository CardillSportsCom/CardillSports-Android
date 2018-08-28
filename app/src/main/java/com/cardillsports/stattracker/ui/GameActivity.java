package com.cardillsports.stattracker.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.data.GameData;

import static com.cardillsports.stattracker.ui.MainActivity.GAME_DATA;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameData gameData = getIntent().getParcelableExtra(GAME_DATA);

        RecyclerView teamOneRecyclerView = findViewById(R.id.team_1_recycler_view);
        teamOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamOneRecyclerView.setAdapter(new PlayerAdapter(gameData.teamOnePlayers()));

        RecyclerView teamTwoRecyclerView = findViewById(R.id.team_2_recycler_view);
        teamTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamTwoRecyclerView.setAdapter(new PlayerAdapter(gameData.teamTwoPlayers()));



    }
}
