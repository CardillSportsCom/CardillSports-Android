package com.cardill.sports.stattracker.boxscore.businesslogic;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.boxscore.data.BoxScoreResponse;
import com.cardill.sports.stattracker.boxscore.data.PlayerStat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Maps a {@link BoxScoreResponse} to a {@link GameData}
 */
class BoxScoreMapper implements Function<BoxScoreResponse, GameData> {

    @Override
    public GameData apply(BoxScoreResponse boxScoreResponse) {
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();

        PlayerStat[] team1Stats = boxScoreResponse.getGameStats()[0].getPlayerStats();
        for (PlayerStat playerStat : team1Stats) {
            Player player = new Player(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getOnePointFieldGoalsMade(),
                    (int)playerStat.getTwoPointFieldGoalsMade(),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team1.add(player);
        }

        PlayerStat[] team2Stats = boxScoreResponse.getGameStats()[1].getPlayerStats();
        for (PlayerStat playerStat : team2Stats) {
            Player player = new Player(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getOnePointFieldGoalsMade(),
                    (int)playerStat.getTwoPointFieldGoalsMade(),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team2.add(player);
        }

        return new GameData(team1, team2, false);
    }
}
