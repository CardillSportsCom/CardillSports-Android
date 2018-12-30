package com.cardill.sports.stattracker.stats.businesslogic;

import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StatsPresenter {
    private final StatsViewBinder viewBinder;
    private final CardillService cardillService;
    private LeagueRepository leagueRepo;

    public StatsPresenter(StatsViewBinder viewBinder, CardillService cardillService, LeagueRepository leagueRepo) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
        this.leagueRepo = leagueRepo;
    }

    public void loadLeagueTotals() {
        Disposable mDisposable = cardillService.getStatTotals(leagueRepo.getActiveLeagueKey())
                .map(new LeagueTotalsMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewBinder::showStats,
                        Timber::e);
    }
}
