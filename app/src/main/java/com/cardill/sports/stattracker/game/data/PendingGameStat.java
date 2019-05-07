package com.cardill.sports.stattracker.game.data;

import com.cardill.sports.stattracker.common.data.InGameStatType;
import com.cardill.sports.stattracker.common.data.Player;

public class PendingGameStat implements GameCommand {
    private final Player player;
    private final InGameStatType inGameStatType;

    public PendingGameStat(Player player, InGameStatType inGameStatType) {
        this.player = player;
        this.inGameStatType = inGameStatType;
    }

    public Player getPlayer() {
        return player;
    }

    public InGameStatType getInGameStatType() {
        return inGameStatType;
    }
}
