package com.cardillsports.stattracker.scores.businesslogic;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.scores.ui.BoxScoreViewBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class BoxScorePresenter {

    private final BoxScoreViewBinder viewBinder;
    private final CardillService cardillService;

    public BoxScorePresenter(BoxScoreViewBinder viewBinder, CardillService cardillService) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
    }

    public void loadBoxScore(String gameId) {
        Disposable mDisposable = cardillService.getBoxScore(gameId)
                .map(new BoxScoreMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewBinder::showBoxScore,
                        Timber::e);
    }
}
