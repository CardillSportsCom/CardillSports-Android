package com.cardillsports.stattracker.stats;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.scores.model.Game;
import com.cardillsports.stattracker.scores.model.GameDay;
import com.cardillsports.stattracker.scores.model.GameDays;

import javax.inject.Inject;

import androidx.navigation.fragment.NavHostFragment;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by vithushan on 9/12/18.
 */

public class GameDayFragment extends Fragment implements GameDayViewBinder {
    public static final String GAME_DAY = "game-day-key";
    public static final String GAME_ID_KEY = "game-id-key";
    private GameDay gameDay;

    private RecyclerView recycler;
    private ProgressBar mProgress;
    @Inject
    CardillService cardillService;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    private GameDayPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        recycler = view.findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgress = view.findViewById(R.id.progress);

        mPresenter = new GameDayPresenter(this, cardillService);

        this.gameDay = (GameDay) getArguments().getSerializable(GAME_DAY);
        loadGames(gameDay);
        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Perform injection here for versions before M as onAttach(*Context*) did not yet exist
            // This fixes DaggerFragment issue: https://github.com/google/dagger/issues/777
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Perform injection here for M (API 23) due to deprecation of onAttach(*Activity*).
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
    }

    public DispatchingAndroidInjector<Fragment> getChildFragmentInjector() {
        return childFragmentInjector;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadGames(GameDay gameDay) {
        mProgress.setVisibility(View.GONE);

        GamesAdapter adapter = new GamesAdapter(gameDay);
        adapter.getEventObservable()
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
