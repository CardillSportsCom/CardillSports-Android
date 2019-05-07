package com.cardill.sports.stattracker.consumer.profile.businesslogic;

import com.cardill.sports.stattracker.consumer.profile.data.PlayerStatResponse;

public interface ProfileViewBinder {
    void showProfile(PlayerStatResponse playerStatResponse);
}
