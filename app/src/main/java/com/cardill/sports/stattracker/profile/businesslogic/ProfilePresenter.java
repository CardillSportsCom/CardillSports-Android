package com.cardill.sports.stattracker.profile.businesslogic;

import com.cardill.sports.stattracker.network.CardillService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ProfilePresenter {

    private ProfileViewBinder mViewBinder;
    private CardillService mCardillService;

    public ProfilePresenter(ProfileViewBinder viewBinder, CardillService cardillService) {
        mViewBinder = viewBinder;
        mCardillService = cardillService;
    }

    public void onLoad(String playerId) {
        Disposable mDisposable = mCardillService.getPlayerStats(playerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mViewBinder::showProfile,
                        Timber::e);
    }
}
