package com.cardill.sports.stattracker.creator.offline.domain.services.networking;

import com.cardill.sports.stattracker.creator.game.data.JSONGameStats;
import com.cardill.sports.stattracker.creator.network.CreatorCardillService;

import java.io.IOException;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by vithushan on 9/12/18.
 */

public class RemoteGameService {


    private static RemoteGameService instance;
    private transient CreatorCardillService service;

    private RemoteGameService(CreatorCardillService service) {
        this.service = service;
    }

    public static synchronized RemoteGameService getInstance(CreatorCardillService service) {
        if (instance == null) {
            instance = new RemoteGameService(service);
        }
        return instance;
    }

    public void saveGameStats(JSONGameStats gameStatscomment) throws IOException, RemoteException {

        Timber.tag("SAVE GAME").d("trying to save game: " + gameStatscomment);
        // Remote call can be executed synchronously since the job calling it is already backgrounded.
        Response response = service.saveGameStats(gameStatscomment).execute();

        if (response == null || !response.isSuccessful() || response.errorBody() != null) {
            throw new RemoteException(response);
        }

        Timber.d("successful remote response: " + response.body());
    }

}
