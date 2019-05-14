package com.cardill.sports.stattracker.consumer.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.consumer.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.consumer.common.data.ConsumerTableUtils;
import com.cardill.sports.stattracker.consumer.network.CardillService;
import com.cardill.sports.stattracker.consumer.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.consumer.stats.businesslogic.StatsViewBinder;
import com.evrencoskun.tableview.TableView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by vithushan on 9/10/18.
 */

public class StatsFragment extends BaseFragment implements StatsViewBinder {

    private TableView tableView;
    private View progress;
    private StatsPresenter mPresenter;

    @Inject
    CardillService cardillService;
    @Inject LeagueRepository leagueRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        mPresenter = new StatsPresenter(this, cardillService, leagueRepo);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadLeagueTotals();
    }

    @Override
    public void showStats(List<ConsumerPlayer> team) {
        progress.setVisibility(View.GONE);

        ConsumerTableUtils.initStatsTable(getActivity(), tableView, team);
    }
}
