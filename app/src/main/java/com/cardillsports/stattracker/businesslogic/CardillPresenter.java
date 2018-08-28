package com.cardillsports.stattracker.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.data.CardillService;
import com.cardillsports.stattracker.data.LeaguePlayersResponse;
import com.cardillsports.stattracker.data.Player;
import com.cardillsports.stattracker.ui.CardillViewBinder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardillPresenter {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";
    private static final String LEAGUE_ID = "5ac6aaefe8da8276a88ffc07";
    private static final String TAG = "Vithushan";

    private final CardillViewBinder mViewBinder;
    private final CardillService mCardillService;
    private Disposable mDisposable;

    public CardillPresenter(CardillViewBinder viewBinder) {
        mViewBinder = viewBinder;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mCardillService = retrofit.create(CardillService.class);

    }

    public void onStart() {
        mDisposable = mCardillService.getPlayersForLeague(LEAGUE_ID)
                .map(resp -> resp.players)
                .flatMapIterable(list -> list)
                .map(item -> item.player)
                .map(player -> Player.create(player._id, player.firstName, player.lastName))
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
}
