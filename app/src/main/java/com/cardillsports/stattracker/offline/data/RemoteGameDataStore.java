package com.cardillsports.stattracker.offline.data;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.businesslogic.RemoteGameRepository;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.offline.domain.services.jobs.JobManagerFactory;
import com.cardillsports.stattracker.offline.domain.services.jobs.SyncGameJob;

import io.reactivex.Completable;

public class RemoteGameDataStore implements RemoteGameRepository {

    @Override
    public Completable sync(GameData gameData) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new SyncGameJob(gameData)));
    }
}
