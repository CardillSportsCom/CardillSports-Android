package com.cardill.sports.stattracker.teamselection.businesslogic;

import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.User;
import com.cardill.sports.stattracker.teamselection.data.Team;
import com.cardill.sports.stattracker.teamselection.data.TeamResponse;
import com.cardill.sports.stattracker.teamselection.ui.TeamSelectionViewBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamSelectionPresenter {

    private static final String TAG = "Vithushan";
    private static final int NUM_OF_TEAMS = 16;

    private final TeamSelectionViewBinder mViewBinder;
    private TeamSelectionViewModel mViewModel;
    private final CardillService mCardillService;
    private Disposable mDisposable;
    private List<User> mTeamOnePlayers;
    private LeagueRepository leagueRepo;

    public TeamSelectionPresenter(TeamSelectionViewBinder viewBinder,
                                  TeamSelectionViewModel viewModel,
                                  CardillService cardillService, LeagueRepository leagueRepo) {
        mViewBinder = viewBinder;
        mViewModel = viewModel;
        mCardillService = cardillService;
        this.leagueRepo = leagueRepo;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getTeamsForLeague(leagueRepo.getActiveLeagueKey(), NUM_OF_TEAMS)
                .map(TeamResponse::getTeams)
                .flatMapIterable((Function<Team[], Iterable<Team>>) Arrays::asList)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        teams -> {
                            mViewModel.setTeams(teams);
                            mViewModel.setLoading(false);
                        },
                        throwable -> Timber.tag(TAG).e(throwable.getLocalizedMessage()));
    }

    public void onStop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void onTeamSelected(Team team) {
        if (mViewModel.isSelectingTeamOne().getValue() == true) {
            mViewModel.isSelectingTeamOne(false);
            mTeamOnePlayers = team.getUsers();

            List<Team> value = mViewModel.getTeams().getValue();
            value.remove(team);
            mViewModel.setTeams(new ArrayList<>(value));
        } else {

            mViewBinder.navigateToGameScreen(mTeamOnePlayers, team.getUsers());
        }
    }
}
