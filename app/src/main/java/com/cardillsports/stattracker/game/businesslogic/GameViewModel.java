package com.cardillsports.stattracker.game.businesslogic;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class GameViewModel extends ViewModel {

    private MutableLiveData<GameState> mGameState;

    public GameViewModel() {
        this.mGameState = new MutableLiveData<>();
        mGameState.setValue(GameState.MAIN);
    }

    public LiveData<GameState> getGameState() {
        return mGameState;
    }

    public void setGameState(GameState gameState) {
        mGameState.setValue(gameState);
    }
}
