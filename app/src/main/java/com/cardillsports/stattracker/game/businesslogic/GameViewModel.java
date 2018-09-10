package com.cardillsports.stattracker.game.businesslogic;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<GameState> mGameState;
    private Team currentTeam;
    private int teamTwoScore;
    private int teamOneScore;
    private MutableLiveData<String> scoreString;

    public GameViewModel() {
        this.mGameState = new MutableLiveData<>();
        mGameState.setValue(GameState.MAIN);
        currentTeam = Team.TEAM_ONE;
        teamOneScore = 0;
        teamTwoScore = 0;
        this.scoreString = new MutableLiveData<>();
        scoreString.setValue("Score: 0 - 0");
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

    public void recordScore() {
        if (currentTeam == Team.TEAM_ONE) {
            teamOneScore++;
        } else {
            teamTwoScore++;
        }
        scoreString.setValue("Score: " + teamOneScore + " - " + teamTwoScore);
    }
}
