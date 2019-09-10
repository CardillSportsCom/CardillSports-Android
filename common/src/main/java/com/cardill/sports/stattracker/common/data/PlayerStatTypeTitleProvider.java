package com.cardill.sports.stattracker.common.data;

import android.support.annotation.StringRes;

import com.cardill.sports.stattracker.common.R;

public class PlayerStatTypeTitleProvider {

    @StringRes
    public static int getTitle(PlayerStatType statType) {
        switch (statType) {
            case WINS:
                return R.string.wins;
            case GP:
                return R.string.games_played;
            case FG_PERCENT:
                return R.string.fg_percent;
            case POINTS:
                return R.string.points;
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
