package com.cardill.sports.stattracker.consumer.boxscore.businesslogic;

import com.cardill.sports.stattracker.common.data.ConsumerGameData;
import com.cardill.sports.stattracker.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.consumer.boxscore.data.BoxScoreResponse;
import com.cardill.sports.stattracker.consumer.boxscore.data.PlayerStat;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.Player;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

/**
 * Maps a {@link BoxScoreResponse} to a {@link GameData}
 */
class BoxScoreMapper implements Function<BoxScoreResponse, ConsumerGameData> {

    @Override
    public ConsumerGameData apply(BoxScoreResponse boxScoreResponse) {
        List<ConsumerPlayer> team1 = new ArrayList<>();
        List<ConsumerPlayer> team2 = new ArrayList<>();

        PlayerStat[] team1Stats = boxScoreResponse.getGameStats()[0].getPlayerStats();
        for (PlayerStat playerStat : team1Stats) {
            ConsumerPlayer player = new ConsumerPlayer(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getPoints(),
                    (int)playerStat.getThreePointersMade(),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team1.add(player);
        }

        PlayerStat[] team2Stats = boxScoreResponse.getGameStats()[1].getPlayerStats();
        for (PlayerStat playerStat : team2Stats) {
            ConsumerPlayer player = new ConsumerPlayer(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getPoints(),
                    (int)playerStat.getThreePointersMade(),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team2.add(player);
        }

        return new ConsumerGameData(team1, team2, false);
    }
}
