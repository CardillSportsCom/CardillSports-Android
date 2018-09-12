package com.cardillsports.stattracker.offline.domain.services;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.data.JSONGameStats;
import com.cardillsports.stattracker.offline.domain.services.networking.RemoteException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by vithushan on 9/12/18.
 */

public class RemoteGameService {
    private static RemoteGameService instance;

    private final Retrofit retrofit;

    public RemoteGameService() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String BASE_URL = "https://api-cardillsports-st.herokuapp.com";

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
