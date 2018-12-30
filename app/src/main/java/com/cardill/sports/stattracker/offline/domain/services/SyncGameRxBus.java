package com.cardill.sports.stattracker.offline.domain.services;

import com.cardill.sports.stattracker.common.data.GameData;
import com.jakewharton.rxrelay2.PublishRelay;

import io.reactivex.Observable;

public class SyncGameRxBus {

    private static SyncGameRxBus instance;
    private final PublishRelay<SyncGameResponse> relay;

    public static synchronized SyncGameRxBus getInstance() {
        if (instance == null) {
            instance = new SyncGameRxBus();
        }
        return instance;
    }

    private SyncGameRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponseEventType eventType, GameData gameData) {
        relay.accept(new SyncGameResponse(eventType, gameData));
    }

    public Observable<SyncGameResponse> toObservable() {
        return relay;
    }
}
