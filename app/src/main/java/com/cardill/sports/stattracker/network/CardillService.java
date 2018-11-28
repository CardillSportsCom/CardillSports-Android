package com.cardill.sports.stattracker.network;

import com.cardill.sports.stattracker.game.data.JSONGameStats;
import com.cardill.sports.stattracker.stats.data.PlayerStatResponse;
import com.cardill.sports.stattracker.teamcreation.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.teamcreation.data.AddTeamRequestBody;
import com.cardill.sports.stattracker.teamcreation.data.LeaguePlayersResponse;
import com.cardill.sports.stattracker.boxscore.data.BoxScoreResponse;
import com.cardill.sports.stattracker.gamedays.data.GameDays;
import com.cardill.sports.stattracker.stats.data.LeagueTotalsResponse;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerResponse;
import com.cardill.sports.stattracker.teamselection.data.TeamResponse;
import com.cardill.sports.stattracker.user.AuthRequestBody;
import com.cardill.sports.stattracker.user.AuthResponseBody;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CardillService {

    @GET("league/players/{leagueID}")
    Observable<LeaguePlayersResponse> getPlayersForLeague(@Path("leagueID") String leagueId);

    @POST("stat")
    Call<ResponseBody> saveGameStats(@Body JSONGameStats jsonGameStats);

    @GET("stat/score/{leagueID}")
    Observable<GameDays> getGameDays(@Path("leagueID") String leagueId);

    @GET("stat/game/{gameID}")
    Observable<BoxScoreResponse> getBoxScore(@Path("gameID") String gameId);

    @GET("stat/league/{leagueId}")
    Observable<LeagueTotalsResponse> getStatTotals(@Path("leagueId") String leagueId);

    @POST("player")
    Observable<AddPlayerResponse> addPlayer(@Body AddPlayerRequestBody addPlayerRequestBody);

    @POST("league/player")
    Observable<ResponseBody> addPlayerToLeague(@Body AddPlayerToLeagueRequestBody addPlayerRequestBody);

    @GET("team/{leagueID}")
    Observable<TeamResponse> getTeamsForLeague(@Path("leagueID") String leagueId, @Query("count") int count);

    @POST("team")
    Observable<ResponseBody> addTeam(@Body AddTeamRequestBody addTeamRequestBody);

    @GET("league")
    Observable<ResponseBody> getLeagues(@Header("Authorization") String token);

    @GET("stat/player/{id}")
    Observable<PlayerStatResponse> getPlayerStats(@Path("id") String playerId);
}
