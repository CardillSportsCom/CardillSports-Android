package com.cardillsports.stattracker.data;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CardillService {

    @GET("league/players/{leagueID}")
    Observable<LeaguePlayersResponse> getPlayersForLeague(@Path("leagueID") String leagueId);
}
