package com.cardill.sports.stattracker.offline.domain.services.networking;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.game.data.JSONGameStats;
import com.cardill.sports.stattracker.network.CardillService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by vithushan on 9/12/18.
 */

public class RemoteGameService {


    private static RemoteGameService instance;

    public static synchronized RemoteGameService getInstance() {
        if (instance == null) {
            instance = new RemoteGameService();
        }
        return instance;
    }

    public void saveGameStats(JSONGameStats gameStatscomment, CardillService service) throws IOException, RemoteException {
        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response response = service.saveGameStats(gameStatscomment).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null) {
            throw new RemoteException(response);
        }

        Timber.d("successful remote response: " + response.body());
    }

}
