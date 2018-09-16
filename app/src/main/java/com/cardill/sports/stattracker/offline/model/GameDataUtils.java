package com.cardill.sports.stattracker.offline.model;

import com.cardill.sports.stattracker.game.data.GameData;


public class GameDataUtils {

    public static GameData clone(GameData gameData, boolean syncPynding) {
        GameData build = new GameData(
                gameData.getId(),
                gameData.getTeamOnePlayers(),
                gameData.getTeamTwoPlayers(),
                syncPynding);

        return build;
    }

    public static GameData clone(GameData gameData, long rowId) {
        GameData build = new GameData(
                rowId,
                gameData.getTeamOnePlayers(),
                gameData.getTeamTwoPlayers(),
                gameData.isSyncPending());
        return build;
    }
}
