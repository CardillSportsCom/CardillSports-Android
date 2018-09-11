package com.cardillsports.stattracker.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by vithushan on 9/10/18.
 */

public class StatsPagerAdapter extends FragmentPagerAdapter {

    public StatsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new StatsFragment() : new ScoresFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
