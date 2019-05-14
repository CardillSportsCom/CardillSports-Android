package com.cardill.sports.stattracker.consumer.boxscore.ui;

import com.cardill.sports.stattracker.consumer.common.data.ConsumerGameData;

public interface BoxScoreViewBinder {
    void showBoxScore(ConsumerGameData gameData);

    void showDeleteGameConfirmation();

    void navigateToScores();
}
