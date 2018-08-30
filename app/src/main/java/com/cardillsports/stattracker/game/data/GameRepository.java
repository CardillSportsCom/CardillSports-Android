package com.cardillsports.stattracker.game.data;

import com.cardillsports.stattracker.common.data.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameRepository {

    private GameData gameData;
    private Queue<PendingStat> queue;

    public GameRepository(GameData gameData) {
        this.gameData = gameData;
        queue = new LinkedList<>();
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

    public void incrementStat(String playerId, StatType statKey) {
        List<Player> teamOnePlayers = gameData.teamOnePlayers();
        incrementTeamStats(playerId, statKey, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.teamTwoPlayers();
        incrementTeamStats(playerId, statKey, teamTwoPlayers);
    }

    private void updateTeamStats(String playerId, StatType statKey, int newValue, List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = updatePlayerStat(statKey, newValue, player);
                playerList.set(i, newPlayer);
            }
        }
    }

    private void incrementTeamStats(String playerId, StatType statKey, List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = incrementPlayerStat(statKey, player);
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

    private Player incrementPlayerStat(StatType statKey, Player player) {
        switch (statKey) {
            case FIELD_GOAL_MADE:
                return player.toBuilder().fieldGoalMade(player.fieldGoalMade() + 1).build();
            case FIELD_GOAL_MISSED:
                return player.toBuilder().fieldGoalMissed(player.fieldGoalMissed() + 1).build();
            case ASSISTS:
                return player.toBuilder().assists(player.assists() + 1).build();
            case REBOUNDS:
                return player.toBuilder().rebounds(player.rebounds() + 1).build();
            case STEALS:
                return player.toBuilder().steals(player.steals() + 1).build();
            case BLOCKS:
                return player.toBuilder().blocks(player.blocks() + 1).build();
            case TURNOVERS:
                return player.toBuilder().turnovers(player.turnovers() + 1).build();

            default:
                return player;
        }
    }

    public void incrementPendingStat(Player player, StatType statType) {
        if (queue.size() > 0) {
            queue.poll();
        }
        queue.offer(new PendingStat(player, statType));
    }

    public PendingStat getLatestPendingStat() {
        return queue.poll();
    }
}
