package com.cardillsports.stattracker.stats.businesslogic;

import com.cardillsports.stattracker.common.data.CardillService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.cardillsports.stattracker.game.data.GameStatsMapper.WEDNESDAY_NIGHTS;

public class StatsPresenter {
    private final StatsViewBinder viewBinder;
    private final CardillService cardillService;

    public StatsPresenter(StatsViewBinder viewBinder, CardillService cardillService) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
    }

    public void loadStatTotals() {
        Disposable mDisposable = cardillService.getStatTotals(WEDNESDAY_NIGHTS)
                .map(new ScoreTotalMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewBinder::showStats,
                        Timber::e);
    }
}
