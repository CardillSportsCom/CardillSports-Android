package com.cardill.sports.stattracker.stats.businesslogic;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.common.data.CardillService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class StatsPresenter {
    private final StatsViewBinder viewBinder;
    private final CardillService cardillService;

    public StatsPresenter(StatsViewBinder viewBinder, CardillService cardillService) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
    }

    public void loadStatTotals() {
        Disposable mDisposable = cardillService.getStatTotals(BuildConfig.LEAGUE_ID)
                .map(new ScoreTotalMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewBinder::showStats,
                        Timber::e);
    }
}
