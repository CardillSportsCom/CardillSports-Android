package com.cardillsports.stattracker.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.data.GameData;
import com.cardillsports.stattracker.data.GameRepository;
import com.cardillsports.stattracker.data.GameStatsMapper;
import com.cardillsports.stattracker.data.JSONGameStats;

public class GamePresenter {
    private final GameRepository gameRepository;

    public GamePresenter(GameRepository gameRepository) {

        this.gameRepository = gameRepository;
    }

    public void submitGameStats() {
        GameData gameData = gameRepository.getGameStats();
        JSONGameStats jsonGameStats = GameStatsMapper.transform(gameData);
        Log.d("VITHUSHAN", jsonGameStats.toString());
    }
}
