package com.cardill.sports.stattracker.scores;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.scores.ScoresViewBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        Disposable mDisposable = cardillService.getGameDays(BuildConfig.LEAGUE_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        scoresViewBinder::loadGameDays,
                        throwable -> Timber.e(throwable));
    }
}
