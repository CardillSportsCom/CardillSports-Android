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
import com.cardillsports.stattracker.scores.model.GameDays;
import com.cardillsports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

import static com.cardillsports.stattracker.stats.GameDayFragment.GAME_DAY;

/**
 * Created by vithushan on 9/10/18.
 */

public class ScoresFragment extends Fragment implements ScoresViewBinder {

    private RecyclerView recycler;
    private ProgressBar mProgress;
    @Inject
    CardillService cardillService;
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;
    private ScoresPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);
        recycler = view.findViewById(R.id.recycler_view);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgress = view.findViewById(R.id.progress);

        mPresenter = new ScoresPresenter(this, cardillService);
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
        mPresenter.loadScores();
    }

    @Override
    public void loadGameDays(GameDays gameDays) {
        mProgress.setVisibility(View.GONE);

        GameDaysAdapter adapter = new GameDaysAdapter(gameDays.getGameDays());
        adapter.getEventObservable()
                .subscribe(gameDay -> gotoGameDay(gameDay));

        recycler.setAdapter(adapter);
        Log.d("VITHUSHAN", gameDays.toString());
    }

    private void gotoGameDay(ScoreEvent.DateSelected dateSelected) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GAME_DAY, dateSelected.getGameDay());
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_scoresFragment_to_gameDayFragment,
                        bundle);

    }
}
