package com.cardill.sports.stattracker.offline.domain.services.networking;

import com.cardill.sports.stattracker.game.data.JSONGameStats;
import com.cardill.sports.stattracker.network.CardillService;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by vithushan on 9/12/18.
 */

public class RemoteGameService {


    private static RemoteGameService instance;
    private transient CardillService service;

    private RemoteGameService(CardillService service) {
        this.service = service;
    }

    public static synchronized RemoteGameService getInstance(CardillService service) {
        if (instance == null) {
            instance = new RemoteGameService(service);
        }
        return instance;
    }

    public void saveGameStats(JSONGameStats gameStatscomment) throws IOException, RemoteException {

        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response response = service.saveGameStats(gameStatscomment).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null) {
            throw new RemoteException(response);
        }

        Timber.d("successful remote response: " + response.body());
    }

}
