package com.cardill.sports.stattracker.boxscore.ui;

import com.cardill.sports.stattracker.common.data.GameData;

public interface BoxScoreViewBinder {
    void showBoxScore(GameData gameData);

    void showDeleteGameConfirmation();

    void navigateToScores();
}
