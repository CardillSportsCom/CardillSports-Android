package com.cardill.sports.stattracker.creator.network;

import com.cardill.sports.stattracker.common.league.PlayerLeaguesResponse;
import com.cardill.sports.stattracker.creator.game.data.JSONGameStats;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerResponse;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.AddTeamRequestBody;
import com.cardill.sports.stattracker.creator.teamcreation.data.LeaguePlayersResponse;
import com.cardill.sports.stattracker.creator.teamselection.data.TeamResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockCreatorCardillService implements CreatorCardillService {
    @Override
    public Observable<LeaguePlayersResponse> getPlayersForLeague(String leagueId) {
        String respString = "{\"players\":[{\"player\":{\"_id\":\"5abeccd77e05fb641c956921\",\"firstName\":\"jason\",\"lastName\":\"rajasegaram\"}},{\"player\":{\"_id\":\"5b8235f58db19f000443df72\",\"firstName\":\"Vithushan\",\"lastName\":\"Namasivayasivam\"}},{\"player\":{\"_id\":\"5b8236128db19f000443df74\",\"firstName\":\"Sujee\",\"lastName\":\"Kalanadan\"}},{\"player\":{\"_id\":\"5b8236318db19f000443df75\",\"firstName\":\"Mith\",\"lastName\":\"Sivanasan\"}},{\"player\":{\"_id\":\"5b8236528db19f000443df77\",\"firstName\":\"Prageen\",\"lastName\":\"Sivabalan\"}},{\"player\":{\"_id\":\"5b8236778db19f000443df78\",\"firstName\":\"Prez\",\"lastName\":\"Karunagaran\"}},{\"player\":{\"_id\":\"5b82368a8db19f000443df79\",\"firstName\":\"Sukee\",\"lastName\":\"Krishnabagavan\"}},{\"player\":{\"_id\":\"5b8236438db19f000443df76\",\"firstName\":\"JV\",\"lastName\":\"Dave\"}}]}\n";
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        LeaguePlayersResponse response = gson.fromJson(respString, LeaguePlayersResponse.class);

        return Observable.just(response);
    }

    //TODO(clean this upP)
    @Override
    public Call<ResponseBody> saveGameStats(JSONGameStats jsonGameStats) {
        return new Call<ResponseBody>() {
            @Override
            public Response<ResponseBody> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<ResponseBody> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<ResponseBody> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    @Override
    public Observable<AddPlayerResponse> addPlayer(AddPlayerRequestBody addPlayerRequestBody) {
        return null;
    }

    @Override
    public Observable<ResponseBody> addPlayerToLeague(AddPlayerToLeagueRequestBody addPlayerRequestBody) {
        return null;
    }

    @Override
    public Observable<TeamResponse> getTeamsForLeague(String leagueId, int count) {
        return null;
    }

    @Override
    public Observable<ResponseBody> addTeam(AddTeamRequestBody addTeamRequestBody) {
        return null;
    }

    @Override
    public Observable<ResponseBody> getLeagues(String token) {
        return null;
    }

    @Override
    public Observable<PlayerLeaguesResponse> getPlayerLeagues(String playerId) {
        return null;
    }
}
