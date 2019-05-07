package com.cardill.sports.stattracker.consumer.stats.businesslogic;

import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.consumer.stats.data.LeagueStat;
import com.cardill.sports.stattracker.consumer.stats.data.LeagueTotalsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class LeagueTotalsMapper implements Function<LeagueTotalsResponse, GameData> {

    @Override
    public GameData apply(LeagueTotalsResponse leagueTotalsResponse) {

        List<Player> team1 = new ArrayList<>();

        LeagueStat[] leagueStats = leagueTotalsResponse.getLeagueStats();
        for (LeagueStat leagueStat : leagueStats) {
            Player player = new Player(
                    leagueStat.getUser().getID(),
                    leagueStat.getUser().getFirstName(),
                    leagueStat.getUser().getLastName(),
                    (int) leagueStat.getPlayerTotalStats().getFGM(),
                    (int) (leagueStat.getPlayerTotalStats().getFGA() - leagueStat.getPlayerTotalStats().getFGM()),
                    (int) leagueStat.getPlayerTotalStats().getAssists(),
                    (int) leagueStat.getPlayerTotalStats().getRebounds(),
                    (int) leagueStat.getPlayerTotalStats().getBlocks(),
                    (int) leagueStat.getPlayerTotalStats().getSteals(),
                    (int) leagueStat.getPlayerTotalStats().getTurnovers(),
                    (int) leagueStat.getPlayerTotalStats().getGamesWon(),
                    (int) leagueStat.getPlayerTotalStats().getGamesPlayed()
            );
            team1.add(player);
        }

        //GameData expects two teams, but for score totals we're just showing all players
        GameData gameData = new GameData(team1, new ArrayList<>(), false);
        return gameData;
    }
}
