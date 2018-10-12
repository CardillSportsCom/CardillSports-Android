package com.cardill.sports.stattracker;

import com.cardill.sports.stattracker.boxscore.ui.BoxScoreFragment;
import com.cardill.sports.stattracker.gamedays.ui.DailyStatsFragment;
import com.cardill.sports.stattracker.gamedays.ui.GameDayFragment;
import com.cardill.sports.stattracker.profile.ProfileFragment;
import com.cardill.sports.stattracker.scores.ScoresFragment;
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

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragmentInjector();
}
