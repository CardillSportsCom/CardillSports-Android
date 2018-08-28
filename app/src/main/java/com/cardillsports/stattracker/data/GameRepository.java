package com.cardillsports.stattracker.data;

import android.util.Log;

import java.util.List;

public class GameRepository {

    GameData gameData;

    public GameRepository(GameData gameData) {
        this.gameData = gameData;
    }

    public GameData getGameStats() {
        return gameData;
    }

    public void updateStats(String playerId, StatType statKey, int newValue) {
        Log.d("VITHUSHAN", playerId + " : " + statKey.name() + " : " + newValue);

        List<Player> teamOnePlayers = gameData.teamOnePlayers();
        for (int i=0; i<teamOnePlayers.size(); i++) {
            Player player = teamOnePlayers.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = updatePlayerStat(statKey, newValue, player);
                teamOnePlayers.set(i, newPlayer);
            }
        }

        Log.d("VITHUSHAN", gameData.toString());
    }

    private Player updatePlayerStat(StatType statKey, int newValue, Player player) {
        switch (statKey) {
            case TWO_POINT_MADE:
                return player.toBuilder().twoPointMade(newValue).build();
            case THREE_POINT_MADE:
                return player.toBuilder().threePointMade(newValue).build();
            case FIELD_GOAL_MISSED:
                return player.toBuilder().fieldGoalMissed(newValue).build();
            case ASSISTS:
                return player.toBuilder().assists(newValue).build();
            case REBOUNDS:
                return player.toBuilder().rebounds(newValue).build();
            case STEALS:
                return player.toBuilder().steals(newValue).build();
            case BLOCKS:
                return player.toBuilder().blocks(newValue).build();
            case TURNOVERS:
                return player.toBuilder().turnovers(newValue).build();

            default:
                return player;
        }
    }
}
