package com.cardill.sports.stattracker.scores.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.scores.model.Game;
import com.cardill.sports.stattracker.scores.model.GameDay;
import com.cardill.sports.stattracker.scores.businesslogic.GamesAdapter;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import dagger.android.DispatchingAndroidInjector;
import io.reactivex.disposables.Disposable;

/**
 * Created by vithushan on 9/12/18.
 */

public class GameDayFragment extends BaseFragment implements GameDayViewBinder {
    public static final String GAME_DAY = "game-day-key";
    public static final String GAME_ID_KEY = "game-id-key";
    private GameDay gameDay;

    private RecyclerView recycler;
    private ProgressBar mProgress;
    @Inject
    CardillService cardillService;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        recycler = view.findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mProgress = view.findViewById(R.id.progress);

        this.gameDay = (GameDay) getArguments().getSerializable(GAME_DAY);
        loadGames(gameDay);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadGames(GameDay gameDay) {
        mProgress.setVisibility(View.GONE);

        GamesAdapter adapter = new GamesAdapter(gameDay);
        Disposable subscribe = adapter.getEventObservable()
                .subscribe(game -> gotoGame(game.getGameDay()));

        recycler.setAdapter(adapter);
    }

    private void gotoGame(Game game) {
        Bundle bundle = new Bundle();
        bundle.putString(GAME_ID_KEY, game.getID());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_gameDayFragment_to_boxScoreFragment,
                        bundle);
    }
}
