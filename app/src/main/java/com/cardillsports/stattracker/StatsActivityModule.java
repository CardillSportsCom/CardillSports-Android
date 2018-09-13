package com.cardillsports.stattracker;

import com.cardillsports.stattracker.scores.ui.BoxScoreFragment;
import com.cardillsports.stattracker.scores.ui.GameDayFragment;
import com.cardillsports.stattracker.scores.ui.ScoresFragment;
import com.cardillsports.stattracker.stats.StatsFragment;

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

    @ContributesAndroidInjector
    abstract BoxScoreFragment boxScoreFragmentInjector();

    @ContributesAndroidInjector
    abstract StatsFragment statsFragmentInjector();
}
