package com.cardill.sports.stattracker.teamselection.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.game.ui.GameActivity;
import com.cardill.sports.stattracker.common.data.User;
import com.cardill.sports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;
import com.cardill.sports.stattracker.teamselection.businesslogic.TeamSelectionViewModel;
import com.cardill.sports.stattracker.teamselection.data.Team;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TeamSelectionActivity extends AppCompatActivity implements TeamSelectionViewBinder {

    public static final String GAME_DATA = "game-data-key";

    private TeamSelectionPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private View mProgress;
    private TeamAdapter mTeamAdapter;

    @Inject CardillService cardillService;
    private TeamSelectionViewModel teamSelectionViewModel;
    @Inject LeagueRepository leagueRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        teamSelectionViewModel = ViewModelProviders.of(this).get(TeamSelectionViewModel.class);
        teamSelectionViewModel.getTeams().observe(this, this::renderUI);
        teamSelectionViewModel.isSelectingTeamOne().observe(this, this::renderAppBarTitle);
        teamSelectionViewModel.isLoading().observe(this, this::renderLoading);

        mRecyclerView = findViewById(R.id.recycler_view);

        mTeamAdapter = new TeamAdapter();
        mTeamAdapter.getEventObservable().subscribe(x -> mPresenter.onTeamSelected(x.getTeam()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mRecyclerView.setAdapter(mTeamAdapter);

        mProgress = findViewById(R.id.progress);

        mPresenter = new TeamSelectionPresenter(this, teamSelectionViewModel, cardillService, leagueRepo);
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

    @Override
    public void onBackPressed() {
        if (teamSelectionViewModel.isSelectingTeamOne().getValue()) {
            finish();
        } else {
            teamSelectionViewModel.isSelectingTeamOne().setValue(true);
            mPresenter.loadPlayers();
        }
    }

    private void renderLoading(Boolean isLoading) {
        if (isLoading) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.GONE);
        }
    }

    private void renderAppBarTitle(Boolean isSelectingTeam1) {
        if (isSelectingTeam1) {
            getSupportActionBar().setTitle(R.string.select_team_1);
        } else {
            getSupportActionBar().setTitle(R.string.select_team_2);
        }
    }

    private void renderUI(List<Team> teams) {
        TeamAdapter teamAdapter = (TeamAdapter) mRecyclerView.getAdapter();
        teamAdapter.setTeams(teams);

    }

    @Override
    public void navigateToGameScreen(List<User> team1, List<User> team2) {
        finish();

        Intent intent = new Intent(this, GameActivity.class);
        List<com.cardill.sports.stattracker.common.data.Player> teamOnePlayers = new ArrayList<>();
        for (User user : team1) {
            teamOnePlayers.add(com.cardill.sports.stattracker.common.data.Player.create(user.getID(), user.getFirstName(), user.getLastName()));
        }
        List<com.cardill.sports.stattracker.common.data.Player> teamTwoPlayers = new ArrayList<>();
        for (User user : team2) {
            teamTwoPlayers.add(com.cardill.sports.stattracker.common.data.Player.create(user.getID(), user.getFirstName(), user.getLastName()));
        }
        GameData gameData = new GameData(
                teamOnePlayers,
                teamTwoPlayers,
                true);

        intent.putExtra(GAME_DATA, (Parcelable) gameData);
        startActivity(intent);
    }
}
