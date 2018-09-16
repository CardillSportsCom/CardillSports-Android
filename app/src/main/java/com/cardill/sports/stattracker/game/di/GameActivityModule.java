package com.cardill.sports.stattracker.game.di;

import com.cardill.sports.stattracker.game.businesslogic.GameViewModelFactory;
import com.cardill.sports.stattracker.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.offline.domain.LocalGameRepository;
import com.cardill.sports.stattracker.offline.domain.SaveGameUseCase;
import com.cardill.sports.stattracker.offline.domain.SyncGameUseCase;
import com.cardill.sports.stattracker.offline.domain.services.SyncCommentLifecycleObserver;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class GameActivityModule {

    @Provides
    static GameViewModelFactory provideCommentsViewModelFactory(SaveGameUseCase saveGameUseCase) {
        return new GameViewModelFactory(saveGameUseCase);
    }

    @Provides
    static SyncCommentLifecycleObserver provideSyncCommentLifecycleObserver(SaveGameUseCase saveGameUseCase) {
        return new SyncCommentLifecycleObserver(saveGameUseCase);
    }

    @Provides
    static SaveGameUseCase provideSaveGameUseCase(LocalGameRepository localGameRepository, SyncGameUseCase syncGameUseCase) {
        return new SaveGameUseCase(localGameRepository, syncGameUseCase);
    }

    @Provides
    static SyncGameUseCase provideSyncCommentUseCase(RemoteGameRepository remoteGameRepository) {
        return new SyncGameUseCase(remoteGameRepository);
    }
}