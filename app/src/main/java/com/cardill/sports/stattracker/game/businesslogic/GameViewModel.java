package com.cardill.sports.stattracker.game.businesslogic;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.offline.domain.SaveGameUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GameViewModel extends ViewModel {

    private MutableLiveData<GameState> mGameState;
    private Team currentTeam;
    private MutableLiveData<String> scoreString;
    private MutableLiveData<GameData> gameStats;

    private final SaveGameUseCase saveGameUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public GameViewModel(SaveGameUseCase saveGameUseCase) {
        this.mGameState = new MutableLiveData<>();
        this.gameStats = new MutableLiveData<>();
        mGameState.setValue(GameState.MAIN);
        currentTeam = Team.TEAM_ONE;
        this.scoreString = new MutableLiveData<>();
        scoreString.setValue("0 - 0");

        this.saveGameUseCase = saveGameUseCase;
    }


    @Override
    protected void onCleared() {
        disposables.clear();
    }

    /**
     * Adds new gameData
     */
    public void saveGame(GameData gameData) {
        disposables.add(saveGameUseCase.saveGame(gameData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("add gameData success"),
                        t -> Timber.e(t, "add gameData error")));
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

        for (Player p : gameStats.getTeamOnePlayers()) {
            team1 += p.fieldGoalMade();
        }

        int team2 = 0;

        for (Player p : gameStats.getTeamTwoPlayers()) {
            team2 += p.fieldGoalMade();
        }

        scoreString.setValue(team1 + " - "  + team2);
    }
}
