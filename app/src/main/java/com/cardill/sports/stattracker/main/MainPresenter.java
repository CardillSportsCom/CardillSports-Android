package com.cardill.sports.stattracker.main;

import com.cardill.sports.stattracker.common.auth.AuthService;
import com.cardill.sports.stattracker.common.league.League;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.common.league.LeagueResponse;
import com.cardill.sports.stattracker.common.league.PlayerLeaguesResponse;
import com.cardill.sports.stattracker.consumer.network.CardillService;
import com.cardill.sports.stattracker.common.auth.AuthRequestBody;
import com.cardill.sports.stattracker.common.auth.Session;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class MainPresenter {

    private MainViewBinder viewBinder;
    private AuthService authService;
    private Session session;
    private CardillService mCardillService;
    private LeagueRepository leagueRepository;
    private MainViewModel viewModel;

    MainPresenter(MainViewBinder viewBinder, AuthService authService, Session session,
                  CardillService mCardillService, LeagueRepository leagueRepository,
                  MainViewModel viewModel) {
        this.viewBinder = viewBinder;
        this.authService = authService;
        this.session = session;
        this.mCardillService = mCardillService;
        this.leagueRepository = leagueRepository;
        this.viewModel = viewModel;
    }

    void leaguePickerRequested() {
        viewBinder.showLeaguePicker();
    }

    void authenticateAndGetLeagues(FirebaseUser user) {
        user.getIdToken(true)
                .addOnCompleteListener(task -> {
                    String token = task.getResult().getToken();
                    Disposable disposable = authService.authenticate(new AuthRequestBody(token))
                            .doOnNext(response -> session.saveToken(response.getId_token()))
                            .flatMap(response -> mCardillService.getPlayerLeagues(response.getPlayer().getId()))
                            .flatMapIterable(PlayerLeaguesResponse::getLeagues)
                            .map(LeagueResponse::getLeague)
                            .toList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::onLeaguesLoaded,
                                    throwable -> Timber.tag("VITHUSHAN").e(throwable));
                });
    }

    private void onLeaguesLoaded(List<League> leagueList) {
        String activeLeague = leagueRepository.getActiveLeagueKey();
        if (activeLeague.isEmpty()) {
            leagueRepository.saveActiveLeaguekey(leagueList.get(0).getID());
        }

        for (League league : leagueList) {
            if (leagueRepository.getActiveLeagueKey().equals(league.getID())) {
                viewModel.setTitle(league.getName());
                break;
            }
        }

        viewBinder.setLeagues(leagueList);
    }

    void handleNoUser() {
        viewBinder.launchSignInIntent();
    }
}
