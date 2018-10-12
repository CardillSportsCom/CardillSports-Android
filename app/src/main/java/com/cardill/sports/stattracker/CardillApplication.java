package com.cardill.sports.stattracker;

import android.app.Activity;
import android.app.Application;

import com.cardill.sports.stattracker.game.data.GameRepository;
import com.cardill.sports.stattracker.offline.domain.services.jobs.JobManagerFactory;
import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.MobileAds;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class CardillApplication extends Application implements HasActivityInjector {

    private static final String AD_APP_ID = "ca-app-pub-6796265721314281~3269435758";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    GameRepository gameRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        DaggerAppComponent.builder().create(this).inject(this);

        JobManagerFactory.getJobManager(this);

        MobileAds.initialize(this, AD_APP_ID);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
