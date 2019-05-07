package com.cardill.sports.stattracker.creator.game.ui;

public interface GameViewBinder {
    void showStatConfirmation(String message);

    void showExitConfirmation();

    void showDetails();

    void showBoxScore();

    void showGameOverConfirmation();

    void showPlayerList();
}
