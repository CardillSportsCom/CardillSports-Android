package com.cardill.sports.stattracker.teamselection.businesslogic;

import com.cardill.sports.stattracker.BuildConfig;
import com.cardill.sports.stattracker.common.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.common.data.AddTeamRequestBody;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamCreationPresenter {

    private static final String TAG = "Vithushan";

    private TeamCreationViewModel mViewModel;
    private final CardillService mCardillService;
    private Disposable mDisposable;
    private TeamCreationViewBinder mViewBinder;

    public TeamCreationPresenter(TeamCreationViewModel viewModel,
                                 CardillService cardillService, TeamCreationViewBinder mViewBinder) {
        mViewModel = viewModel;
        mCardillService = cardillService;
        this.mViewBinder = mViewBinder;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getPlayersForLeague(BuildConfig.LEAGUE_ID)
                .map(resp -> resp.players)
                .flatMapIterable(list -> list)
                .map(item -> item.player)
                .map(player -> Player.create(player._id, player.firstName, player.lastName))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newGamePlayers -> {
                            mViewModel.setPlayers(newGamePlayers);
                            mViewModel.setLoading(false);
                        },
                        throwable -> Timber.tag(TAG).e(throwable.getLocalizedMessage()));
    }

    public void onStop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void onTeamSelected(List<Player> playerList) {
        List<String> playerIds = new ArrayList<>();
        for (Player player : playerList) {
            playerIds.add(player.id());
        }

        mViewModel.isLoading().setValue(true);

        AddTeamRequestBody requestBody = new AddTeamRequestBody("Team", playerIds);
        Disposable subscribe = mCardillService.addTeam(requestBody)
                .doOnError(Timber::e)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                            mViewBinder.askToCreateAnotherTeam();
                        },
                        Timber::e);
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
