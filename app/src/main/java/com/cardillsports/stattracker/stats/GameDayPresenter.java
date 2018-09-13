package com.cardillsports.stattracker.stats;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter.LEAGUE_ID;

/**
 * Created by vithushan on 9/12/18.
 */

public class GameDayPresenter {
    private final GameDayViewBinder viewBinder;
    private final CardillService cardillService;

    public GameDayPresenter(GameDayViewBinder viewBinder, CardillService cardillService) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
    }
}
