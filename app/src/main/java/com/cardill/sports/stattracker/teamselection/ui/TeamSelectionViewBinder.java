package com.cardill.sports.stattracker.teamselection.ui;

import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;

import java.util.List;

public interface TeamSelectionViewBinder {
    void loadPlayers(List<NewGamePlayer> players);

    void navigateToGameScreen();

    void showLoading();
}
