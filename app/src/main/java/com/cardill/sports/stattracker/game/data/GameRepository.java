package com.cardill.sports.stattracker.game.data;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameRepository {

    private GameData gameData;
    private Queue<PendingStat> queue;

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

    public void updateStats(String playerId, StatType statKey, int newValue) {
        List<Player> teamOnePlayers = gameData.getTeamOnePlayers();
        updateTeamStats(playerId, statKey, newValue, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.getTeamTwoPlayers();
        updateTeamStats(playerId, statKey, newValue, teamTwoPlayers);
    }

    public void incrementStat(String playerId, StatType statKey) {
        List<Player> teamOnePlayers = gameData.getTeamOnePlayers();
        incrementTeamStats(playerId, statKey, teamOnePlayers);

        List<Player> teamTwoPlayers = gameData.getTeamTwoPlayers();
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
            case FGM:
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

    private Player incrementPlayerStat(StatType statKey, Player player) {
        switch (statKey) {
            case FGM:
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

    public void incrementPendingStat(Player player, StatType statType) {
        if (queue.size() > 0) {
            queue.poll();
        }
        queue.offer(new PendingStat(player, statType));
    }

    public PendingStat getLatestPendingStat() {
        return queue.poll();
    }

    public int getGameStat(Player player, StatType statType) {
        List<Player> teamOnePlayers = gameData.getTeamOnePlayers();
        for (Player p1 : teamOnePlayers) {
            if (p1.id().equals(player.id())) {
                switch (statType) {
                    case TO:
                        return p1.turnovers();
                    case REB:
                        return p1.rebounds();
                    case STL:
                        return p1.steals();
                    case AST:
                        return p1.assists();
                    case MISSES:
                        return p1.fieldGoalMissed();
                    case FGM:
                        return p1.fieldGoalMade();
                    case BLK:
                        return p1.blocks();
                }
            }
        }


        List<Player> teamTwoPlayers = gameData.getTeamTwoPlayers();
        for (Player p2 : teamTwoPlayers) {
            if (p2.id().equals(player.id())) {
                switch (statType) {
                    case TO:
                        return p2.turnovers();
                    case REB:
                        return p2.rebounds();
                    case STL:
                        return p2.steals();
                    case AST:
                        return p2.assists();
                    case MISSES:
                        return p2.fieldGoalMissed();
                    case FGM:
                        return p2.fieldGoalMade();
                    case BLK:
                        return p2.blocks();
                }
            }
        }

        throw new IllegalStateException("Stat not found");
    }
}
