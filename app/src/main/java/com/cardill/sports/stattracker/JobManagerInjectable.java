package com.cardill.sports.stattracker;

import com.cardill.sports.stattracker.network.CardillService;

public interface JobManagerInjectable {
    void inject(CardillService component);
}