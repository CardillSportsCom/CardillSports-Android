package com.cardill.sports.stattracker.scores.businesslogic;

import android.util.Log;

import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.scores.ui.ScoresViewBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.cardill.sports.stattracker.teamselection.businesslogic.TeamSelectionPresenter.LEAGUE_ID;

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
                        throwable -> Timber.e(throwable));
    }
}
