package com.cardill.sports.stattracker.network;

import com.cardill.sports.stattracker.boxscore.data.BoxScoreResponse;
import com.cardill.sports.stattracker.common.data.User;
import com.cardill.sports.stattracker.common.league.PlayerLeaguesResponse;
import com.cardill.sports.stattracker.gamedays.data.GameDays;
import com.cardill.sports.stattracker.profile.data.PlayerStat;
import com.cardill.sports.stattracker.profile.data.PlayerStatResponse;
import com.cardill.sports.stattracker.stats.data.LeagueTotalsResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MockCardillService implements CardillService {

    @Override
    public Observable<GameDays> getGameDays(String leagueId) {
        return Observable.empty();
    }

    @Override
    public Observable<BoxScoreResponse> getBoxScore(String gameId) {
        return Observable.empty();
    }

    @Override
    public Observable<LeagueTotalsResponse> getStatTotals(String gameId) {
        return Observable.empty();
    }

    @Override
    public Observable<ResponseBody> getLeagues(String token) {
        return Observable.empty();
    }

    @Override
    public Observable<PlayerStatResponse> getPlayerStats(String playerId) {
        PlayerStatResponse playerStatResponse = new PlayerStatResponse();
        PlayerStat playerStat = new PlayerStat();
        User player = new User();
        player.setFirstName("VITHUSHAN");
        player.setLastName("NAMA");
        playerStat.setDateCreated("05/06/2019");
        playerStat.setPlayer(player);
        playerStat.setFGM(1);
        playerStat.setFGA(2);
        playerStat.setPoints(3);
        playerStat.setTwoPointersMade(4);
        playerStat.setAssists(5);
        playerStat.setRebounds(6);
        playerStat.setSteals(7);
        playerStat.setBlocks(8);
        playerStat.setTurnovers(9);

        PlayerStat[] playerStats = {playerStat};
        playerStatResponse.setPlayerStats(playerStats);
        return Observable.just(playerStatResponse);
    }

    @Override
    public Observable<PlayerLeaguesResponse> getPlayerLeagues(String playerId) {
        return Observable.empty();
    }

}
