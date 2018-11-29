package com.cardill.sports.stattracker.offline.data;

import com.birbit.android.jobqueue.JobManager;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.offline.domain.services.jobs.JobManagerFactory;
import com.cardill.sports.stattracker.offline.domain.services.jobs.SyncGameJob;

import io.reactivex.Completable;

public class RemoteGameDataStore implements RemoteGameRepository {

    private JobManager jobManager;
    private transient final CardillService service;

    public RemoteGameDataStore(JobManager jobManager, CardillService service) {
        this.jobManager = jobManager;
        this.service = service;
    }

    @Override
    public Completable sync(GameData gameData) {
        return Completable.fromAction(() ->
                jobManager.addJobInBackground(new SyncGameJob(gameData, service)));
    }
}
