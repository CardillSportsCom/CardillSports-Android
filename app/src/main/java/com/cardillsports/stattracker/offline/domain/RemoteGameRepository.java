package com.cardillsports.stattracker.offline.domain;

import com.cardillsports.stattracker.game.data.GameData;

import io.reactivex.Completable;

public interface RemoteGameRepository {
    Completable sync(GameData gameData);
}