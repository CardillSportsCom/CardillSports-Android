package com.cardillsports.stattracker;

import com.cardillsports.stattracker.stats.GameDayFragment;
import com.cardillsports.stattracker.stats.ScoresFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vithushan on 9/12/18.
 */

@Module
public abstract class StatsActivityModule {
    @ContributesAndroidInjector
    abstract ScoresFragment scoresFragmentInjector();


    @ContributesAndroidInjector
    abstract GameDayFragment gameDayFragmentInjector();
}
