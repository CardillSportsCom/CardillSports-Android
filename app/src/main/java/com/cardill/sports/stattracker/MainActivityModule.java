package com.cardill.sports.stattracker;

import com.cardill.sports.stattracker.scores.ui.BoxScoreFragment;
import com.cardill.sports.stattracker.scores.ui.DailyStatsFragment;
import com.cardill.sports.stattracker.scores.ui.GameDayFragment;
import com.cardill.sports.stattracker.scores.ui.ScoresFragment;
import com.cardill.sports.stattracker.stats.StatsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vithushan on 9/12/18.
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract ScoresFragment scoresFragmentInjector();

    @ContributesAndroidInjector
    abstract GameDayFragment gameDayFragmentInjector();

    @ContributesAndroidInjector
    abstract DailyStatsFragment dailyStatsFragmentInjector();

    @ContributesAndroidInjector
    abstract BoxScoreFragment boxScoreFragmentInjector();

    @ContributesAndroidInjector
    abstract StatsFragment statsFragmentInjector();
}
