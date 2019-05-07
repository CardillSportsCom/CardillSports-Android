package com.cardill.sports.stattracker.consumer.boxscore.ui;

import com.cardill.sports.stattracker.common.data.ConsumerGameData;
import com.cardill.sports.stattracker.common.data.GameData;

public interface BoxScoreViewBinder {
    void showBoxScore(ConsumerGameData gameData);

    void showDeleteGameConfirmation();

    void navigateToScores();
}
