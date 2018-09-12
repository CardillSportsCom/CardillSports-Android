package com.cardillsports.stattracker.offline.domain;

import com.cardillsports.stattracker.game.businesslogic.RemoteGameRepository;
import com.cardillsports.stattracker.game.data.GameData;

import io.reactivex.Completable;

/**
 * Responsible for syncing a gameData with remote repository.
 */
public class SyncGameUseCase {
    private final RemoteGameRepository remoteGameRepository;

    public SyncGameUseCase(RemoteGameRepository remoteGameRepository) {
        this.remoteGameRepository = remoteGameRepository;
    }

    public Completable syncGame(GameData gameData) {
        return remoteGameRepository.sync(gameData);
    }
}
