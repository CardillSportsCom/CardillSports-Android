package com.cardill.sports.stattracker.game.ui;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.teamcreation.businesslogic.PlayerComparator;
import com.cardill.sports.stattracker.teamcreation.businesslogic.PlayersViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

class PlayerListPresenter{

    private final PlayersViewModel mViewModel;
    private final CardillService mCardillService;
    private final LeagueRepository leagueRepository;
    private Disposable mDisposable;

    public PlayerListPresenter(PlayersViewModel viewModel, CardillService cardillService, LeagueRepository leagueRepository) {
        this.mViewModel = viewModel;
        this.mCardillService = cardillService;
        this.leagueRepository = leagueRepository;
    }

    void loadPlayers() {

        mDisposable = mCardillService.getPlayersForLeague(leagueRepository.getActiveLeagueKey())
                .map(resp -> resp.players)
                .flatMapIterable(list -> list)
                .map(item -> item.player)
                .map(player -> Player.create(player._id, player.firstName, player.lastName))
                .toList()
                .map(unsortedList -> {
                    List<Player> sortedList = new ArrayList<>(unsortedList);
                    Collections.sort(sortedList, new PlayerComparator());
                    return sortedList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newGamePlayers -> {
                            mViewModel.setPlayers(newGamePlayers);
                            mViewModel.setLoading(false);
                        },
                        throwable -> Timber.tag("VITHUSHAN").e(throwable.getLocalizedMessage()));
    }

    void onStop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
