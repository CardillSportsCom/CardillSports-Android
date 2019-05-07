package com.cardill.sports.stattracker.creator.network;

import com.cardill.sports.stattracker.common.league.PlayerLeaguesResponse;
import com.cardill.sports.stattracker.creator.game.data.JSONGameStats;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerResponse;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddTeamRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.LeaguePlayersResponse;
import com.cardill.sports.stattracker.creator.teamselection.data.TeamResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CreatorCardillService {

    @GET("league/players/{leagueID}")
    Observable<LeaguePlayersResponse> getPlayersForLeague(@Path("leagueID") String leagueId);

    @POST("stat")
    Call<ResponseBody> saveGameStats(@Body JSONGameStats jsonGameStats);

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

    @GET("player/leagues/{playerID}")
    Observable<PlayerLeaguesResponse> getPlayerLeagues(@Path("playerID") String playerId);
}
