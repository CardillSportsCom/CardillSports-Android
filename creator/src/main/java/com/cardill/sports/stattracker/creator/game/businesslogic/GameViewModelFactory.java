package com.cardill.sports.stattracker.creator.game.businesslogic;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cardill.sports.stattracker.creator.offline.domain.SaveGameUseCase;

public class GameViewModelFactory implements ViewModelProvider.Factory {

    private final SaveGameUseCase saveGameUseCase;

    public GameViewModelFactory(SaveGameUseCase saveGameUseCase1) {
        this.saveGameUseCase = saveGameUseCase1;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GameViewModel.class)) {
            return (T) new GameViewModel(saveGameUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}