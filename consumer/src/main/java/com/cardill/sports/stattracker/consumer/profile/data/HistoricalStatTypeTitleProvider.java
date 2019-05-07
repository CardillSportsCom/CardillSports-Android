package com.cardill.sports.stattracker.consumer.profile.data;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;

import com.cardill.sports.stattracker.consumer.R;

public class HistoricalStatTypeTitleProvider {

    @StringRes
    public static int getTitle(HistoricalStatType statType) {
        switch (statType) {
            case FG_PERCENT:
                return R.string.fg_percent;
            case POINTS:
                return R.string.points;
            case THREES:
                return R.string.threes;
            case AST:
                return R.string.assists;
            case REB:
                return R.string.rebounds;
            case STL:
                return R.string.steals;
            case BLK:
                return R.string.blocks;
            case TO:
                return R.string.turnovers;
        }

        return R.string.turnovers;
    }
}
