package com.cardillsports.stattracker.game.ui;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.businesslogic.GameState;

public interface GameViewBinder {
    void showStatConfirmation(Player player, String value);
}
