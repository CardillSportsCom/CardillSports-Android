package com.cardill.sports.stattracker.game.data;

import com.cardill.sports.stattracker.common.data.GameStatType;
import com.cardill.sports.stattracker.common.data.Player;

public class PendingGameStat implements GameCommand {
    private final Player player;
    private final GameStatType gameStatType;

    public PendingGameStat(Player player, GameStatType gameStatType) {
        this.player = player;
        this.gameStatType = gameStatType;
    }

    public Player getPlayer() {
        return player;
    }

    public GameStatType getGameStatType() {
        return gameStatType;
    }
}
