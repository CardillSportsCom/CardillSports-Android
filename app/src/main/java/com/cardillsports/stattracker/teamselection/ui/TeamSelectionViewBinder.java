package com.cardillsports.stattracker.teamselection.ui;

import com.cardillsports.stattracker.teamselection.data.NewGamePlayer;

import java.util.List;

public interface TeamSelectionViewBinder {
    void loadPlayers(List<NewGamePlayer> players);

    void navigateToGameScreen();

    void showLoading();
}
