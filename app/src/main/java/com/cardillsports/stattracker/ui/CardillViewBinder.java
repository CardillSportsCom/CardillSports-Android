package com.cardillsports.stattracker.ui;

import com.cardillsports.stattracker.data.Player;
import com.cardillsports.stattracker.data.PlayerResponse;

import java.util.List;

public interface CardillViewBinder {
    void loadPlayers(List<Player> players);
}
