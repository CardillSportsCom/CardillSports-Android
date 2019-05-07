package com.cardill.sports.stattracker.creator.offline.domain.services.jobs;

import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.creator.network.CreatorCardillService;

public interface JobManagerInjectable {
    void inject(CreatorCardillService component, LeagueRepository leagueRepository);
}
