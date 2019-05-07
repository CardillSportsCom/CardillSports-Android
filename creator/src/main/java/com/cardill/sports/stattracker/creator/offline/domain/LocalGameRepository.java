package com.cardill.sports.stattracker.creator.offline.domain;

import com.cardill.sports.stattracker.common.data.GameData;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Responsible for CRUD operations against local gameData repository
 */
public interface LocalGameRepository {
    Single<GameData> add(GameData gameData);

    Single<ArrayList<GameData>> get();

}
