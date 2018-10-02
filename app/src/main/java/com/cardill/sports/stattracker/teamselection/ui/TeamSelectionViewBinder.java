package com.cardill.sports.stattracker.teamselection.ui;


import com.cardill.sports.stattracker.stats.data.Player;

import java.util.List;

public interface TeamSelectionViewBinder {
    void navigateToGameScreen(List<Player> team1, List<Player> team2);
}
