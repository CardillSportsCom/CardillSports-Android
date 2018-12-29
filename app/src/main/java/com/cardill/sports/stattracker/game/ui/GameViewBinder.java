package com.cardill.sports.stattracker.game.ui;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.game.businesslogic.GameState;

public interface GameViewBinder {
    void showStatConfirmation(String message);

    void showExitConfirmation();

    void showDetails();

    void showBoxScore();

    void showGameOverConfirmation();

    void showPlayerList();
}
