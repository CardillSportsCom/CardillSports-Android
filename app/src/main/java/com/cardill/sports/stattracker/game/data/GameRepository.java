package com.cardill.sports.stattracker.game.data;

import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.GameStatType;
import com.cardill.sports.stattracker.common.data.Player;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameRepository {

    private GameData gameData;
    private LinkedList<PendingGameStat> queue;

    @Inject
    public GameRepository() {
        queue = new LinkedList<>();
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    @Nullable
    public GameData getGameStats() {
        return gameData;
    }

    public void updateStats(String playerId, GameStatType statKey, int newValue) {
        List<Player> teamOnePlayers = gameData.getTeamOnePlayers();
        updateTeamStats(playerId, statKey, newValue, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.getTeamTwoPlayers();
        updateTeamStats(playerId, statKey, newValue, teamTwoPlayers);
    }

    public void incrementStat(String playerId, GameStatType statKey) {
        List<Player> teamOnePlayers = gameData.getTeamOnePlayers();
        incrementTeamStats(playerId, statKey, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.getTeamTwoPlayers();
        incrementTeamStats(playerId, statKey, teamTwoPlayers);
    }

    private void updateTeamStats(String playerId, GameStatType statKey, int newValue, List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = updatePlayerStat(statKey, newValue, player);
                playerList.set(i, newPlayer);
            }
        }
    }

    private void incrementTeamStats(String playerId, GameStatType statKey, List<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            if (player.id().equals(playerId)) {
                Player newPlayer = incrementPlayerStat(statKey, player);
                playerList.set(i, newPlayer);
            }
        }
    }

    private Player updatePlayerStat(GameStatType statKey, int newValue, Player player) {
        switch (statKey) {
            case MAKES:
                return player.toBuilder().fieldGoalMade(newValue).build();
            case MISSES:
                return player.toBuilder().fieldGoalMissed(newValue).build();
            case AST:
                return player.toBuilder().assists(newValue).build();
            case REB:
                return player.toBuilder().rebounds(newValue).build();
            case STL:
                return player.toBuilder().steals(newValue).build();
            case BLK:
                return player.toBuilder().blocks(newValue).build();
            case TO:
                return player.toBuilder().turnovers(newValue).build();

            default:
                return player;
        }
    }

    private Player incrementPlayerStat(GameStatType statKey, Player player) {
        switch (statKey) {
            case MAKES:
                return player.toBuilder().fieldGoalMade(player.fieldGoalMade() + 1).build();
            case MISSES:
                return player.toBuilder().fieldGoalMissed(player.fieldGoalMissed() + 1).build();
            case AST:
                return player.toBuilder().assists(player.assists() + 1).build();
            case REB:
                return player.toBuilder().rebounds(player.rebounds() + 1).build();
            case STL:
                return player.toBuilder().steals(player.steals() + 1).build();
            case BLK:
                return player.toBuilder().blocks(player.blocks() + 1).build();
            case TO:
                return player.toBuilder().turnovers(player.turnovers() + 1).build();

            default:
                return player;
        }
    }

    public void incrementPendingStat(Player player, GameStatType gameStatType) {
        queue.offer(new PendingGameStat(player, gameStatType));
    }

    public PendingGameStat getLatestPendingStat() {
        return queue.poll();
    }

    public PendingGameStat removeFromQueue() {
        return queue.removeLast();
    }
}
