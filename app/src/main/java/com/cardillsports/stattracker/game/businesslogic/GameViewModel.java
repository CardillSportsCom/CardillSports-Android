package com.cardillsports.stattracker.game.businesslogic;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<GameState> mGameState;
    private Team currentTeam;

    public GameViewModel() {
        this.mGameState = new MutableLiveData<>();
        mGameState.setValue(GameState.MAIN);
        currentTeam = Team.TEAM_ONE;
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
}
