package com.cardillsports.stattracker.offline.domain;

import com.cardillsports.stattracker.game.data.GameData;

import io.reactivex.Completable;

public class SaveGameUseCase {
    private final LocalGameRepository localGameRepository;
    private final SyncGameUseCase syncGameUseCase;

    public SaveGameUseCase(LocalGameRepository localGameRepository, SyncGameUseCase syncGameUseCase) {
        this.localGameRepository = localGameRepository;
        this.syncGameUseCase = syncGameUseCase;
    }

    public Completable saveGame(GameData gameData) {
        return localGameRepository.add(gameData)
                .flatMapCompletable(syncGameUseCase::syncGame);
    }
}
