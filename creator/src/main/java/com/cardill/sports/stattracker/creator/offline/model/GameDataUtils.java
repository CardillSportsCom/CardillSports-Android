package com.cardill.sports.stattracker.creator.offline.model;

import com.cardill.sports.stattracker.common.data.GameData;


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
