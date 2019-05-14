package com.cardill.sports.stattracker.consumer.stats.businesslogic;

import com.cardill.sports.stattracker.consumer.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.consumer.stats.data.LeagueStat;
import com.cardill.sports.stattracker.consumer.stats.data.LeagueTotalsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class LeagueTotalsMapper implements Function<LeagueTotalsResponse, List<ConsumerPlayer>> {

    @Override
    public List<ConsumerPlayer> apply(LeagueTotalsResponse leagueTotalsResponse) {

        List<ConsumerPlayer> team1 = new ArrayList<>();

        LeagueStat[] leagueStats = leagueTotalsResponse.getLeagueStats();
        for (LeagueStat leagueStat : leagueStats) {
            ConsumerPlayer player = new ConsumerPlayer(
                    leagueStat.getUser().getID(),
                    leagueStat.getUser().getFirstName(),
                    leagueStat.getUser().getLastName(),
                    (int) leagueStat.getPlayerTotalStats().getGamesWon(),
                    (int) leagueStat.getPlayerTotalStats().getGamesPlayed(),
                    (int) leagueStat.getPlayerTotalStats().getFGM(),
                    (int) leagueStat.getPlayerTotalStats().getFGA(),
                    0,
                    (int) leagueStat.getPlayerTotalStats().getThreePointersMade(),
                    (int) leagueStat.getPlayerTotalStats().getAssists(),
                    (int) leagueStat.getPlayerTotalStats().getRebounds(),
                    (int) leagueStat.getPlayerTotalStats().getBlocks(),
                    (int) leagueStat.getPlayerTotalStats().getSteals(),
                    (int) leagueStat.getPlayerTotalStats().getTurnovers()
            );
            team1.add(player);
        }

        return team1;
    }
}
