package com.cardillsports.stattracker.teamselection.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.common.data.AddPlayerToLeagueRequestBody;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardillsports.stattracker.teamselection.data.NewGamePlayer;
import com.cardillsports.stattracker.teamselection.ui.TeamSelectionViewBinder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class TeamSelectionPresenter {

    public static final String LEAGUE_ID = "5ac6aaefe8da8276a88ffc07";
    private static final String TAG = "Vithushan";

    private final TeamSelectionViewBinder mViewBinder;
    private final CardillService mCardillService;
    private Disposable mDisposable;

    public TeamSelectionPresenter(TeamSelectionViewBinder viewBinder, CardillService cardillService) {
        mViewBinder = viewBinder;
        mCardillService = cardillService;
    }

    public void loadPlayers() {

        mDisposable = mCardillService.getPlayersForLeague(LEAGUE_ID)
                .map(resp -> resp.players)
                .flatMapIterable(list -> list)
                .map(item -> item.player)
                .map(player -> new NewGamePlayer(Player.create(player._id, player.firstName, player.lastName), false, false))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mViewBinder::loadPlayers,
                        throwable -> Log.e(TAG, throwable.getLocalizedMessage()));
    }

    public void onStop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public void onTeamsSelected() {
        mViewBinder.navigateToGameScreen();
    }


    public void addPlayer(String playerName) {
        AddPlayerRequestBody addPlayerRequestBody = new AddPlayerRequestBody(playerName, "lastName", playerName + "@gmail.com", "password");
        Disposable subscribe = mCardillService.addPlayer(addPlayerRequestBody)
                .map(addPlayerResponse -> addPlayerResponse.getNewPlayer().getID())
                .flatMap(playerId -> mCardillService.addPlayerToLeague(new AddPlayerToLeagueRequestBody(playerId, LEAGUE_ID)))
                .doOnError(Timber::e)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    mViewBinder.showLoading();
                    loadPlayers();
                        },
                        Timber::e);
    }
}
