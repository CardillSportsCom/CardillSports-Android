package com.cardillsports.stattracker.game.businesslogic;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.GameData;

public class GameViewModel extends ViewModel {

    private MutableLiveData<GameState> mGameState;
    private Team currentTeam;
    private int teamTwoScore;
    private int teamOneScore;
    private MutableLiveData<String> scoreString;
    private MutableLiveData<GameData> gameStats;

    public GameViewModel() {
        this.mGameState = new MutableLiveData<>();
        this.gameStats = new MutableLiveData<>();
        mGameState.setValue(GameState.MAIN);
        currentTeam = Team.TEAM_ONE;
        teamOneScore = 0;
        teamTwoScore = 0;
        this.scoreString = new MutableLiveData<>();
        scoreString.setValue("0 - 0");
    }

    public LiveData<GameState> getGameState() {
        return mGameState;
    }

    public void setGameState(GameState gameState) {
        mGameState.setValue(gameState);
    }

    public void setCurrentTeam(Team currentTeam) {
        this.currentTeam = currentTeam;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public LiveData<String> getCurrentScore() {
        return scoreString;
    }

    public void updateScore(GameData gameStats) {

        this.gameStats.setValue(gameStats);

        int team1 = 0;

        for (Player p : gameStats.teamOnePlayers()) {
            team1 += p.fieldGoalMade();
        }

        int team2 = 0;

        for (Player p : gameStats.teamTwoPlayers()) {
            team2 += p.fieldGoalMade();
        }

        scoreString.setValue(team1 + " - "  + team2);
    }
}
