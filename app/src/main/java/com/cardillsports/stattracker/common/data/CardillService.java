package com.cardillsports.stattracker.common.data;

import com.cardillsports.stattracker.game.data.JSONGameStats;
import com.cardillsports.stattracker.scores.model.boxscore.BoxScoreResponse;
import com.cardillsports.stattracker.scores.model.GameDays;
import com.cardillsports.stattracker.stats.data.LeagueTotalsResponse;
import com.cardillsports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardillsports.stattracker.teamselection.data.AddPlayerResponse;
import com.cardillsports.stattracker.teamselection.data.LeaguePlayersResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CardillService {

    @GET("league/players/{leagueID}")
    Observable<LeaguePlayersResponse> getPlayersForLeague(@Path("leagueID") String leagueId);

    @POST("stat")
    Call<ResponseBody> saveGameStats(@Body JSONGameStats jsonGameStats);

    @GET("stat/score/{leagueID}")
    Observable<GameDays> getGameDays(@Path("leagueID") String leagueId);

    @GET("stat/game/{gameID}")
    Observable<BoxScoreResponse> getBoxScore(@Path("gameID") String gameId);

    @GET("stat/league/{gameID}")
    Observable<LeagueTotalsResponse> getStatTotals(@Path("gameID") String gameId);

    @POST("player")
    Observable<AddPlayerResponse> addPlayer(@Body AddPlayerRequestBody addPlayerRequestBody);

    @POST("league/player")
    Observable<ResponseBody> addPlayerToLeague(@Body AddPlayerToLeagueRequestBody addPlayerRequestBody);
}
