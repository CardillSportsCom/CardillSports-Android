package com.cardill.sports.stattracker.creator.game.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.cardill.sports.stattracker.creator.R;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.creator.network.CreatorCardillService;
import com.cardill.sports.stattracker.creator.teamcreation.businesslogic.PlayersViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class PlayerListActivity extends AppCompatActivity {
    public static final String GAME_DATA = "game-data-key";
    public static final String SUB_IN_PLAYER_EXTRA_KEY = "sub-in-player-extra-key";
    public static final String SUB_OUT_PLAYER_EXTRA_KEY = "sub-out-player-extra-key";

    private PlayerListPresenter mPresenter;
    private View mProgress;
    private PlayerAdapter mPlayerAdapter;

    @Inject
    CreatorCardillService cardillService;

    @Inject
    LeagueRepository leagueRepository;

    private PlayersViewModel viewModel;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        Player subbedOutPlayer = getIntent().getParcelableExtra(SUB_OUT_PLAYER_EXTRA_KEY);

        viewModel = ViewModelProviders.of(this).get(PlayersViewModel.class);
        viewModel.getPlayers().observe(this, this::renderUI);
        viewModel.isLoading().observe(this, this::renderLoading);

        mListView = findViewById(R.id.list_view);

        mPlayerAdapter = new PlayerAdapter(this);
        mListView.setAdapter(mPlayerAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Player player = (Player) parent.getAdapter().getItem(position);
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(SUB_OUT_PLAYER_EXTRA_KEY, subbedOutPlayer);
            bundle.putParcelable(SUB_IN_PLAYER_EXTRA_KEY, player);
            data.putExtras(bundle);
            setResult(RESULT_OK, data);
            finish();
        });

        mProgress = findViewById(R.id.progress);

        mPresenter = new PlayerListPresenter(viewModel, cardillService, leagueRepository);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadPlayers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    private void renderLoading(Boolean isLoading) {
        if (isLoading) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.GONE);
        }
    }

    private void renderUI(List<Player> players) {
        mPlayerAdapter.setPlayers(players);
    }
}
