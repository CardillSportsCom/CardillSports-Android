package com.cardillsports.stattracker.game.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.data.GameStatsMapper;
import com.cardillsports.stattracker.game.data.JSONGameStats;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GamePresenter {
    private final GameRepository gameRepository;
    private final CardillService mCardillService;

    public GamePresenter(GameRepository gameRepository, CardillService cardillService) {

        this.gameRepository = gameRepository;
        mCardillService = cardillService;
    }

    public void submitGameStats() {
        GameData gameData = gameRepository.getGameStats();
        JSONGameStats jsonGameStats = GameStatsMapper.transform(gameData);
        Disposable subscribe = mCardillService.saveGameStats(jsonGameStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> Log.d("VITHUSHAN", "submitGameStats: " + x.string()),
                        x -> Log.e("VITHUSHAN", x.getLocalizedMessage()));
    }

}