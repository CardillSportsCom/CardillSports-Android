package com.cardill.sports.stattracker;

import android.app.Activity;
import android.app.Application;

import com.cardill.sports.stattracker.creator.game.data.GameRepository;
import com.cardill.sports.stattracker.creator.offline.domain.services.jobs.JobManagerFactory;
import com.cardill.sports.stattracker.user.Session;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class CardillApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    GameRepository gameRepository;

    private Session session;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        DaggerAppComponent.builder().create(this).inject(this);

        JobManagerFactory.getJobManager(this);

        Stetho.initializeWithDefaults(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
