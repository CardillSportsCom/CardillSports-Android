package com.cardillsports.stattracker.businesslogic;

import com.cardillsports.stattracker.data.GameRepository;

public class GamePresenter {
    private final GameRepository gameRepository;

    public GamePresenter(GameRepository gameRepository) {

        this.gameRepository = gameRepository;
    }

    public void submitGameStats() {

    }
}
