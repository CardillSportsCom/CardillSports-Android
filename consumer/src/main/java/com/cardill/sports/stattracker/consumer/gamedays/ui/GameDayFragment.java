package com.cardill.sports.stattracker.consumer.gamedays.ui;

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

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.consumer.common.data.Game;
import com.cardill.sports.stattracker.consumer.common.data.GameDay;
import com.cardill.sports.stattracker.consumer.common.data.GameDayStatTotal;
import com.cardill.sports.stattracker.consumer.network.CardillService;

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
    public static final String GAME_DAY_STAT_TOTALS_ID_KEY = "game-day-stat-total-id-key";

    private GameDay gameDay;

    private RecyclerView recycler;
    private ProgressBar mProgress;
    @Inject
    CardillService cardillService;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    private View mDailyTotals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_day, container, false);
        recycler = view.findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mProgress = view.findViewById(R.id.progress);
        mDailyTotals = view.findViewById(R.id.daily_totals);
        View mDailyStatsImage = view.findViewById(R.id.daily_stats_image);
        View mDailyStatsText = view.findViewById(R.id.daily_stats_text);

        this.gameDay = (GameDay) getArguments().getSerializable(GAME_DAY);
        loadGames(gameDay);

        mDailyTotals.setOnClickListener(v -> gotoDailyStats(gameDay.getGameDayStatTotals()));
        mDailyStatsImage.setOnClickListener(v -> gotoDailyStats(gameDay.getGameDayStatTotals()));
        mDailyStatsText.setOnClickListener(v -> gotoDailyStats(gameDay.getGameDayStatTotals()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void loadGames(GameDay gameDay) {
        mProgress.setVisibility(View.GONE);
        mDailyTotals.setVisibility(View.VISIBLE);

        GamesAdapter adapter = new GamesAdapter(gameDay);
        Disposable subscribe = adapter.getEventObservable()
                .subscribe(game -> gotoGame(game.getGameDay()));

        recycler.setAdapter(adapter);
    }

    private void gotoDailyStats(GameDayStatTotal[] gameDayStatTotals) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GAME_DAY_STAT_TOTALS_ID_KEY, gameDayStatTotals);
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_gameDayFragment_to_dailyStatsFragment,
                        bundle);
    }

    private void gotoGame(Game game) {
        Bundle bundle = new Bundle();
        bundle.putString(GAME_ID_KEY, game.getID());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_gameDayFragment_to_boxScoreFragment,
                        bundle);
    }
}
