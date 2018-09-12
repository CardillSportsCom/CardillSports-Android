package com.cardillsports.stattracker.offline.domain;

import com.cardillsports.stattracker.game.data.GameData;

import io.reactivex.Single;

/**
 * Responsible for CRUD operations against local gameData repository
 */
public interface LocalGameRepository {
    Single<GameData> add(GameData gameData);
}
