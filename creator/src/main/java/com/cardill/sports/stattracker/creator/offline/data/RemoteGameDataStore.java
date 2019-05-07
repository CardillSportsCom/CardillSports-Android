package com.cardill.sports.stattracker.creator.offline.data;

import com.birbit.android.jobqueue.JobManager;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.creator.network.CreatorCardillService;
import com.cardill.sports.stattracker.creator.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.creator.offline.domain.services.jobs.SyncGameJob;

import io.reactivex.Completable;

public class RemoteGameDataStore implements RemoteGameRepository {

    private JobManager jobManager;
    private transient final CreatorCardillService service;

    public RemoteGameDataStore(JobManager jobManager, CreatorCardillService service) {
        this.jobManager = jobManager;
        this.service = service;
    }

    @Override
    public Completable sync(GameData gameData) {
        return Completable.fromAction(() ->
                jobManager.addJobInBackground(new SyncGameJob(gameData, service)));
    }
}
