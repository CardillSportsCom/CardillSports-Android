package com.cardill.sports.stattracker.creator.offline.domain;

import com.cardill.sports.stattracker.common.data.GameData;

import io.reactivex.Completable;

public interface RemoteGameRepository {
    Completable sync(GameData gameData);
}