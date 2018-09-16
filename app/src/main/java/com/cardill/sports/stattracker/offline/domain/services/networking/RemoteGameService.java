package com.cardill.sports.stattracker.offline.domain.services.networking;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.JSONGameStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Comment;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by vithushan on 9/12/18.
 */

public class RemoteGameService {


    private static RemoteGameService instance;

    private final Retrofit retrofit;

    public RemoteGameService() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RemoteGameService getInstance() {
        if (instance == null) {
            instance = new RemoteGameService();
        }
        return instance;
    }

    public void saveGameStats(JSONGameStats gameStatscomment) throws IOException, RemoteException {
        CardillService service = retrofit.create(CardillService.class);

        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response response = service.saveGameStats(gameStatscomment).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null) {
            throw new RemoteException(response);
        }

        Timber.d("successful remote response: " + response.body());
    }

}
