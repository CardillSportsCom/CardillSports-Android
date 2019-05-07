package com.cardill.sports.stattracker.creator.offline.domain.services.jobs;

import android.support.annotation.NonNull;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SchedulerJobService extends FrameworkJobSchedulerService {

    @Inject
    public SchedulerJobService() {
    }

    @NonNull
    @Override
    protected JobManager getJobManager() {
        return JobManagerFactory.getJobManager();
    }
}
