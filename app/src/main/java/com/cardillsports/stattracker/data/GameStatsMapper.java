package com.cardillsports.stattracker.data;

import java.util.ArrayList;
import java.util.List;

public class GameStatsMapper {

    public static final String WEDNESDAY_NIGHTS = "5ac6aaefe8da8276a88ffc07";
    public static final String BASKETBALL_ID = "5ac7e74c9acd8e7d202b26ae";

    public static JSONGameStats transform(GameData gameData) {
        JSONGameStats jsonGameStats = new JSONGameStats();
        jsonGameStats.leagueId = WEDNESDAY_NIGHTS;
        jsonGameStats.teamTypeId = BASKETBALL_ID;

        jsonGameStats.teamA = transformTeamStats(gameData.teamOnePlayers(), "Team 1");
        jsonGameStats.teamB = transformTeamStats(gameData.teamTwoPlayers(), "Team 2");

        return jsonGameStats;
    }

    private static JSONTeamStats transformTeamStats(List<Player> playerList, String name) {
        JSONTeamStats jsonTeamStats = new JSONTeamStats();

        jsonTeamStats.name = name;
        jsonTeamStats.players = transformPlayerStats(playerList);

        return jsonTeamStats;
    }

    private static List<JSONPlayerStats> transformPlayerStats(List<Player> playerList) {
        List<JSONPlayerStats> result = new ArrayList<>();

        for (int i=0; i<playerList.size(); i++) {
            Player player = playerList.get(i);

            JSONPlayerStats jsonPlayerStats = new JSONPlayerStats();
            jsonPlayerStats.assists = player.assists();
            jsonPlayerStats.blocks = player.blocks();
            jsonPlayerStats.fga = player.fieldGoalMissed();
            jsonPlayerStats.fgm = player.fieldGoalMade();
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
