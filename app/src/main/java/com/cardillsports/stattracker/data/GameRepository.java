package com.cardillsports.stattracker.data;

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
        List<Player> teamOnePlayers = gameData.teamOnePlayers();
        updateTeamStats(playerId, statKey, newValue, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.teamTwoPlayers();
        updateTeamStats(playerId, statKey, newValue, teamTwoPlayers);
    }

    private void updateTeamStats(String playerId, StatType statKey, int newValue, List<Player> playerList) {
        for (int i=0; i<playerList.size(); i++) {
            Player player = playerList.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = updatePlayerStat(statKey, newValue, player);
                playerList.set(i, newPlayer);
            }
        }
    }

    private Player updatePlayerStat(StatType statKey, int newValue, Player player) {
        switch (statKey) {
            case FIELD_GOAL_MADE:
                return player.toBuilder().fieldGoalMade(newValue).build();
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
