package com.cardill.sports.stattracker.creator.offline.domain.services;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.creator.offline.domain.SaveGameUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Updates local database after remote gameData sync requests
 */
public class SyncCommentLifecycleObserver implements LifecycleObserver {
    private final SaveGameUseCase saveGameUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public SyncCommentLifecycleObserver(SaveGameUseCase saveGameUseCase) {
        this.saveGameUseCase = saveGameUseCase;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.d("onResume lifecycle event.");
        disposables.add(SyncGameRxBus.getInstance().toObservable()
                .subscribe(this::handleSyncResponse, t -> Timber.e(t, "error handling sync response")));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.d("onPause lifecycle event.");
        disposables.clear();
    }

    private void handleSyncResponse(SyncGameResponse response) {
        if (response.eventType == SyncResponseEventType.SUCCESS) {
            onSyncCommentSuccess(response.gameData);
        }
    }

    private void onSyncCommentSuccess(GameData gameData) {
        Timber.d("received sync gameData success event for gameData %s", gameData);
        disposables.add(saveGameUseCase.saveGame(gameData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Timber.d("update gameData success"),
                        t -> Timber.e(t, "update gameData error")));
    }
}
