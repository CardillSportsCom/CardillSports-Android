package com.cardill.sports.stattracker.teamselection.businesslogic;

import com.cardill.sports.stattracker.common.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.teamselection.ui.TeamSelectionViewBinder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamCreationPresenter {

    public static final String LEAGUE_ID = "5ac6aaefe8da8276a88ffc07";
    private static final String TAG = "Vithushan";

    private final TeamSelectionViewBinder mViewBinder;
    private TeamCreationViewModel mViewModel;
    private final CardillService mCardillService;
    private Disposable mDisposable;
    private List<Player> mTeamOnePlayers;

    public TeamCreationPresenter(TeamSelectionViewBinder viewBinder,
                                 TeamCreationViewModel viewModel,
                                 CardillService cardillService) {
        mViewBinder = viewBinder;
        mViewModel = viewModel;
        mCardillService = cardillService;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getPlayersForLeague(LEAGUE_ID)
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

    }

    public void addPlayer(AddPlayerRequestBody addPlayerRequestBody) {
        Disposable subscribe = mCardillService.addPlayer(addPlayerRequestBody)
                .map(addPlayerResponse -> addPlayerResponse.getNewPlayer().getID())
                .flatMap(playerId -> mCardillService.addPlayerToLeague(new AddPlayerToLeagueRequestBody(playerId, LEAGUE_ID)))
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
