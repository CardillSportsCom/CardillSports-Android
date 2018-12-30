package com.cardill.sports.stattracker.teamcreation.businesslogic;

import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.teamcreation.data.AddPlayerToLeagueRequestBody;
import com.cardill.sports.stattracker.teamcreation.data.AddTeamRequestBody;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamcreation.data.AddPlayerRequestBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamCreationPresenter {

    private static final String TAG = "Vithushan";

    private PlayersViewModel mViewModel;
    private final CardillService mCardillService;
    private Disposable mDisposable;
    private TeamCreationViewBinder mViewBinder;
    private LeagueRepository leagueRepo;

    public TeamCreationPresenter(PlayersViewModel viewModel,
                                 CardillService cardillService, TeamCreationViewBinder mViewBinder, LeagueRepository leagueRepo) {
        mViewModel = viewModel;
        mCardillService = cardillService;
        this.mViewBinder = mViewBinder;
        this.leagueRepo = leagueRepo;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getPlayersForLeague(leagueRepo.getActiveLeagueKey())
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

        AddTeamRequestBody requestBody = new AddTeamRequestBody("Team", playerIds, leagueRepo.getActiveLeagueKey());
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
                .flatMap(playerId -> mCardillService.addPlayerToLeague(new AddPlayerToLeagueRequestBody(playerId, leagueRepo.getActiveLeagueKey())))
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
