package com.cardill.sports.stattracker.offline.data;

import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.offline.domain.LocalGameRepository;
import com.cardill.sports.stattracker.offline.model.GameDataUtils;

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
            Timber.d("gameData stored " + rowId);
            return GameDataUtils.clone(gameData, rowId);
        });
    }
}