package com.cardill.sports.stattracker.offline.domain.services.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.GameStatsMapper;
import com.cardill.sports.stattracker.game.data.JSONGameStats;
import com.cardill.sports.stattracker.offline.domain.services.SyncGameRxBus;
import com.cardill.sports.stattracker.offline.domain.services.SyncResponseEventType;
import com.cardill.sports.stattracker.offline.domain.services.networking.RemoteException;
import com.cardill.sports.stattracker.offline.domain.services.networking.RemoteGameService;
import com.cardill.sports.stattracker.offline.model.GameDataUtils;

import timber.log.Timber;

public class SyncGameJob extends Job {

    private static final String TAG = SyncGameJob.class.getCanonicalName();
    private final GameData gameData;

    public SyncGameJob(GameData gameData) {
        super(new Params(JobPriority.MID)
                .requireNetwork()
                .groupBy(TAG)
                .persist());
        this.gameData = gameData;
    }

    @Override
    public void onAdded() {
        Timber.d("Executing onAdded() for gameData " + gameData);
    }

    @Override
    public void onRun() throws Throwable {
        Timber.d("Executing onRun() for gameData " + gameData);

        // if any exception is thrown, it will be handled by shouldReRunOnThrowable()
        JSONGameStats jsonGameStats = GameStatsMapper.transform(gameData);

        RemoteGameService.getInstance().saveGameStats(jsonGameStats);

        // remote call was successful--the GameData will be updated locally to reflect that sync is no longer pending
        GameData updateGameData = GameDataUtils.clone(gameData, false);
        SyncGameRxBus.getInstance().post(SyncResponseEventType.SUCCESS, updateGameData);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Timber.d("canceling job. reason: %d, throwable: %s", cancelReason, throwable);
        // sync to remote failed
        SyncGameRxBus.getInstance().post(SyncResponseEventType.FAILED, gameData);
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        if(throwable instanceof RemoteException) {
            RemoteException exception = (RemoteException) throwable;

            int statusCode = exception.getResponse().code();
            if (statusCode >= 400 && statusCode < 500) {
                return RetryConstraint.CANCEL;
            }
        }
        // if we are here, most likely the connection was lost during job execution
        return RetryConstraint.RETRY;
    }
}
