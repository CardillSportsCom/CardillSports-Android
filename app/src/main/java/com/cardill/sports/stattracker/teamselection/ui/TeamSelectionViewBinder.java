package com.cardill.sports.stattracker.teamselection.ui;


import com.cardill.sports.stattracker.common.data.User;

import java.util.List;

public interface TeamSelectionViewBinder {
    void navigateToGameScreen(List<User> team1, List<User> team2);
}
