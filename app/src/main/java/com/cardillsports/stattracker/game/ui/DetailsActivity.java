package com.cardillsports.stattracker.game.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.game.businesslogic.GamePlayerAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class DetailsActivity extends AppCompatActivity {

    @Inject GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        GameData gameData = gameRepository.getGameStats();

        if (gameData != null) {
            RecyclerView teamOneRecyclerView = findViewById(R.id.team_1_recycler_view);
            teamOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            GamePlayerAdapter teamOneAdapter = new GamePlayerAdapter(gameData.teamOnePlayers(), gameRepository);
            teamOneRecyclerView.setAdapter(teamOneAdapter);

            RecyclerView teamTwoRecyclerView = findViewById(R.id.team_2_recycler_view);
            teamTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            GamePlayerAdapter teamTwoAdapter = new GamePlayerAdapter(gameData.teamTwoPlayers(), gameRepository);
            teamTwoRecyclerView.setAdapter(teamTwoAdapter);
        }
    }
}

