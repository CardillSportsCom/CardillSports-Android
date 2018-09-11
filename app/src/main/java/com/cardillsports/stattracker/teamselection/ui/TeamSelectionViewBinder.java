package com.cardillsports.stattracker.teamselection.ui;

import com.cardillsports.stattracker.common.data.Player;

import java.util.List;

public interface TeamSelectionViewBinder {
    void loadPlayers(List<Player> players);

    void navigateToGameScreen();
}
