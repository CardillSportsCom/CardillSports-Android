package com.cardill.sports.stattracker.consumer.scores;

import com.cardill.sports.stattracker.consumer.common.data.GameDays;

/**
 * Created by vithushan on 9/12/18.
 */

public interface ScoresViewBinder {
    void loadGameDays(GameDays gameDays);
}
