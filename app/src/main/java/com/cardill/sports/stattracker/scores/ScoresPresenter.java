package com.cardill.sports.stattracker.scores;

import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;

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
    private LeagueRepository leagueRepo;

    public ScoresPresenter(ScoresViewBinder scoresViewBinder, CardillService cardillService, LeagueRepository leagueRepo) {
        this.scoresViewBinder = scoresViewBinder;
        this.cardillService = cardillService;
        this.leagueRepo = leagueRepo;
    }

    public void loadScores() {
        Disposable mDisposable = cardillService.getGameDays(leagueRepo.getActiveLeagueKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        scoresViewBinder::loadGameDays,
                        throwable -> Timber.e(throwable));
    }
}
