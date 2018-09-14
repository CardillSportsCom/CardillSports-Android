package com.cardillsports.stattracker.stats.businesslogic;

import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.stats.data.LeagueStat;
import com.cardillsports.stattracker.stats.data.LeagueTotalsResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class ScoreTotalMapper implements Function<LeagueTotalsResponse, GameData> {

    @Override
    public GameData apply(LeagueTotalsResponse leagueTotalsResponse) {

        List<Player> team1 = new ArrayList<>();

        LeagueStat[] leagueStats = leagueTotalsResponse.getLeagueStats();
        for (LeagueStat leagueStat : leagueStats) {
            Player player = new Player(
                    leagueStat.getPlayer().getPlayerID(),
                    leagueStat.getPlayer().getFirstName(),
                    leagueStat.getPlayer().getLastName(),
                    (int) leagueStat.getPlayerTotalStats().getFGM(),
                    (int) (leagueStat.getPlayerTotalStats().getFGA() - leagueStat.getPlayerTotalStats().getFGM()),
                    (int) leagueStat.getPlayerTotalStats().getAssists(),
                    (int) leagueStat.getPlayerTotalStats().getRebounds(),
                    (int) leagueStat.getPlayerTotalStats().getBlocks(),
                    (int) leagueStat.getPlayerTotalStats().getSteals(),
                    (int) leagueStat.getPlayerTotalStats().getTurnovers()
            );
            team1.add(player);
        }

        //GameData expects two teams, but for score totals we're just showing all players
        GameData gameData = new GameData(team1, new ArrayList<>(), false);
        return gameData;
    }
}
