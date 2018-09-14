package com.cardillsports.stattracker.game.ui;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.businesslogic.GameState;

public interface GameViewBinder {
    void showStatConfirmation(String message);

    void showExitConfirmation();

    void showDetails();

    void showBoxScore();
}
