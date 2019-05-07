package com.cardill.sports.stattracker.creator.offline.data;

import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.creator.offline.domain.LocalGameRepository;
import com.cardill.sports.stattracker.creator.offline.model.GameDataUtils;

import java.util.ArrayList;

import io.reactivex.Single;
import timber.log.Timber;

public class LocalGameDataStore implements LocalGameRepository {

    private final GameDataDao gameDataDao;

    public LocalGameDataStore(GameDataDao gameDataDao) {
        this.gameDataDao = gameDataDao;
    }

    /**
     * Adds a gameData to a given photo
     */
    public Single<GameData> add(GameData gameData) {
        Timber.d("creating gameData %s", gameData);

        return Single.fromCallable(() -> {
            long rowId = gameDataDao.add(gameData);
            Timber.d("gameData stored %s", rowId);
            return GameDataUtils.clone(gameData, rowId);
        });
    }

    @Override
    public Single<ArrayList<GameData>> get() {
        Timber.d("getting gameData");

        return Single.fromCallable(() -> {
            ArrayList<GameData> gameDataList = new ArrayList<>(gameDataDao.get());
            Timber.d("gameData got %s", gameDataList.size());
            return gameDataList;
        });
    }
}