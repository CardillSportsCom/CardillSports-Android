package com.cardillsports.stattracker.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.data.CardillService;
import com.cardillsports.stattracker.data.Player;
import com.cardillsports.stattracker.ui.CardillViewBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CardillPresenter {

    private static final String LEAGUE_ID = "5ac6aaefe8da8276a88ffc07";
    private static final String TAG = "Vithushan";

    private final CardillViewBinder mViewBinder;
    private final CardillService mCardillService;
    private Disposable mDisposable;

    public CardillPresenter(CardillViewBinder viewBinder, CardillService cardillService) {
        mViewBinder = viewBinder;
        mCardillService = cardillService;
    }

    public void onStart() {
        mDisposable = mCardillService.getPlayersForLeague(LEAGUE_ID)
                .map(resp -> resp.players)
                .flatMapIterable(list -> list)
                .map(item -> item.player)
                .map(player -> Player.create(player._id, player.firstName, player.lastName))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mViewBinder::loadPlayers,
                        throwable -> Log.e(TAG, throwable.getLocalizedMessage()));
    }

    public void onStop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
