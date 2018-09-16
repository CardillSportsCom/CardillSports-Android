package com.cardill.sports.stattracker.offline.data;

import com.cardill.sports.stattracker.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.offline.domain.services.jobs.JobManagerFactory;
import com.cardill.sports.stattracker.offline.domain.services.jobs.SyncGameJob;

import io.reactivex.Completable;

public class RemoteGameDataStore implements RemoteGameRepository {

    @Override
    public Completable sync(GameData gameData) {
        return Completable.fromAction(() ->
                JobManagerFactory.getJobManager().addJobInBackground(new SyncGameJob(gameData)));
    }
}
