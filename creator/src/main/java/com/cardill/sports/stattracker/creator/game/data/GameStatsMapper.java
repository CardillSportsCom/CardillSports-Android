package com.cardill.sports.stattracker.creator.game.data;

import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.league.LeagueRepository;

import java.util.ArrayList;
import java.util.List;

public class GameStatsMapper {

    public static final String BASKETBALL_ID = "5ac7e74c9acd8e7d202b26ae";

    public static JSONGameStats transform(GameData gameData, LeagueRepository leagueRepository) {
        JSONGameStats jsonGameStats = new JSONGameStats();
        jsonGameStats.leagueId = leagueRepository.getActiveLeagueKey();
        jsonGameStats.teamTypeId = BASKETBALL_ID;

        jsonGameStats.teamA = transformTeamStats(gameData.getTeamOnePlayers(), "Team 1");
        jsonGameStats.teamB = transformTeamStats(gameData.getTeamTwoPlayers(), "Team 2");

        jsonGameStats.teamAScore = calculateTeamScore(gameData.getTeamOnePlayers());
        jsonGameStats.teamBScore = calculateTeamScore(gameData.getTeamTwoPlayers());

        return jsonGameStats;
    }

    private static String calculateTeamScore(List<Player> teamOnePlayers) {
        int result = 0;

        for (int i=0; i<teamOnePlayers.size(); i++) {
            Player player = teamOnePlayers.get(i);

            result += player.getOnePointFieldGoalMade();
            result += player.getTwoPointFieldGoalMade() * 2;
        }

        return String.valueOf(result);
    }

    private static JSONTeamStats transformTeamStats(List<Player> playerList, String name) {
        JSONTeamStats jsonTeamStats = new JSONTeamStats();

        jsonTeamStats.name = name;
        List<Player> playersWithStats = new ArrayList<>();
        List<Player> playersWithoutStats = new ArrayList<>();

        for (Player player : playerList) {
            if (player.shouldIgnoreStats()) {
                playersWithoutStats.add(player);
            } else {
                playersWithStats.add(player);
            }
        }
        jsonTeamStats.players = transformPlayerStats(playersWithStats);
        jsonTeamStats.substitutes = transformPlayerStats(playersWithoutStats);

        return jsonTeamStats;
    }

    private static List<JSONPlayerStats> transformPlayerStats(List<Player> playerList) {
        List<JSONPlayerStats> result = new ArrayList<>();

        for (int i=0; i<playerList.size(); i++) {
            Player player = playerList.get(i);

            JSONPlayerStats jsonPlayerStats = new JSONPlayerStats();
            jsonPlayerStats.assists = player.assists();
            jsonPlayerStats.blocks = player.blocks();
            jsonPlayerStats.fga = player.fieldGoalMissed() +
                    player.getOnePointFieldGoalMade() +
                    player.getTwoPointFieldGoalMade();
            jsonPlayerStats.fgm = player.getOnePointFieldGoalMade() +
                    player.getTwoPointFieldGoalMade();
            jsonPlayerStats.threePointersMade = player.getTwoPointFieldGoalMade();
            jsonPlayerStats.threePointersAttempted = 0;
            jsonPlayerStats.playerId = player.id();
            jsonPlayerStats.rebounds = player.rebounds();
            jsonPlayerStats.steals = player.steals();
            jsonPlayerStats.blocks = player.blocks();
            jsonPlayerStats.turnovers = player.turnovers();

            result.add(jsonPlayerStats);
        }

        return result;
    }
}
