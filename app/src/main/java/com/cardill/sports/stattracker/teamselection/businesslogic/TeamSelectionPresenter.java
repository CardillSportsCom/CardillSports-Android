package com.cardill.sports.stattracker.teamselection.businesslogic;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.common.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.stats.data.Player;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;
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

    private final TeamSelectionViewBinder mViewBinder;
    private TeamSelectionViewModel mViewModel;
    private final CardillService mCardillService;
    private Disposable mDisposable;
    private List<Player> mTeamOnePlayers;

    public TeamSelectionPresenter(TeamSelectionViewBinder viewBinder,
                                  TeamSelectionViewModel viewModel,
                                  CardillService cardillService) {
        mViewBinder = viewBinder;
        mViewModel = viewModel;
        mCardillService = cardillService;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getTeamsForLeague()
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
            mTeamOnePlayers = team.getPlayers();

            List<Team> value = mViewModel.getTeams().getValue();
            value.remove(team);
            mViewModel.setTeams(new ArrayList<>(value));
        } else {

            mViewBinder.navigateToGameScreen(mTeamOnePlayers, team.getPlayers());
        }
    }

    public void addPlayer(AddPlayerRequestBody addPlayerRequestBody) {
        Disposable subscribe = mCardillService.addPlayer(addPlayerRequestBody)
                .map(addPlayerResponse -> addPlayerResponse.getNewPlayer().getID())
                .flatMap(playerId -> mCardillService.addPlayerToLeague(new AddPlayerToLeagueRequestBody(playerId, BuildConfig.LEAGUE_ID)))
                .doOnError(Timber::e)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    mViewModel.setLoading(true);
                    loadPlayers();
                        },
                        Timber::e);
    }
}
