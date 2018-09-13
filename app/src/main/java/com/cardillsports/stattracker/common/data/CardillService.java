package com.cardillsports.stattracker.common.data;

import com.cardillsports.stattracker.game.data.JSONGameStats;
import com.cardillsports.stattracker.scores.model.GameDays;

import io.reactivex.Flowable;
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
}
