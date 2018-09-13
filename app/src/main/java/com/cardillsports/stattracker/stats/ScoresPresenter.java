package com.cardillsports.stattracker.stats;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter.LEAGUE_ID;

/**
 * Created by vithushan on 9/12/18.
 */

public class ScoresPresenter {
    private final ScoresViewBinder scoresViewBinder;
    private final CardillService cardillService;

    public ScoresPresenter(ScoresViewBinder scoresViewBinder, CardillService cardillService) {
        this.scoresViewBinder = scoresViewBinder;
        this.cardillService = cardillService;
    }


    public void loadScores() {
        Disposable mDisposable = cardillService.getGameDays(LEAGUE_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        scoresViewBinder::loadGameDays,
                        throwable -> Log.e("VITHUSHAN", throwable.getLocalizedMessage()));
    }
}
