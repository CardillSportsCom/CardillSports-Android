package com.cardillsports.stattracker.main.ui;

import com.cardillsports.stattracker.common.data.Player;

import java.util.List;

public interface MainViewBinder {
    void loadPlayers(List<Player> players);

    void navigateToGameScreen();
}
