package com.cardill.sports.stattracker.consumer.network;

import com.cardill.sports.stattracker.consumer.boxscore.data.BoxScoreResponse;
import com.cardill.sports.stattracker.common.league.PlayerLeaguesResponse;
import com.cardill.sports.stattracker.consumer.common.data.GameDays;
import com.cardill.sports.stattracker.consumer.profile.data.PlayerStatResponse;
import com.cardill.sports.stattracker.consumer.stats.data.LeagueTotalsResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CardillService {

    @GET("stat/score/{leagueID}")
    Observable<GameDays> getGameDays(@Path("leagueID") String leagueId);

    @GET("stat/game/{gameID}")
    Observable<BoxScoreResponse> getBoxScore(@Path("gameID") String gameId);

    @GET("stat/league/{leagueId}")
    Observable<LeagueTotalsResponse> getStatTotals(@Path("leagueId") String leagueId);

    @GET("league")
    Observable<ResponseBody> getLeagues(@Header("Authorization") String token);

    @GET("stat/player/{id}")
    Observable<PlayerStatResponse> getPlayerStats(@Path("id") String playerId);

    @GET("player/leagues/{playerID}")
    Observable<PlayerLeaguesResponse> getPlayerLeagues(@Path("playerID") String playerId);
}
