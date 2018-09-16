package com.cardill.sports.stattracker.offline.domain;

import com.cardill.sports.stattracker.game.data.GameData;

import io.reactivex.Completable;

public interface RemoteGameRepository {
    Completable sync(GameData gameData);
}