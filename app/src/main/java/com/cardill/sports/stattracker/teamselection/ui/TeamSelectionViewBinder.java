package com.cardill.sports.stattracker.teamselection.ui;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;

import java.util.List;

public interface TeamSelectionViewBinder {
    void navigateToGameScreen(List<Player> team1, List<Player> team2);
}
