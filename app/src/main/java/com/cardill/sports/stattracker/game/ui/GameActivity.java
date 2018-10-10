package com.cardill.sports.stattracker.game.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.details.ui.DetailsActivity;
import com.cardill.sports.stattracker.game.businesslogic.GameEvent;
import com.cardill.sports.stattracker.game.businesslogic.GamePresenter;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.game.businesslogic.GameState;
import com.cardill.sports.stattracker.game.businesslogic.GameViewModel;
import com.cardill.sports.stattracker.game.businesslogic.GameViewModelFactory;
import com.cardill.sports.stattracker.game.businesslogic.NewGamePlayerAdapter;
import com.cardill.sports.stattracker.game.businesslogic.Team;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.GameRepository;
import com.cardill.sports.stattracker.offline.domain.services.SyncCommentLifecycleObserver;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.cardill.sports.stattracker.teamselection.ui.TeamSelectionActivity.GAME_DATA;

public class GameActivity extends AppCompatActivity implements GameViewBinder {

    private static final String IS_ONLINE_KEY = "is-online-key";

    private GamePresenter mPresenter;
    private Button makeButton;
    private Button missButton;
    private Button turnoverButton;
    private RecyclerView teamTwoRecyclerView;
    private RecyclerView teamOneRecyclerView;
    private View assistButton;
    private View noAssistButton;
    private View team1TextView;
    private View team2TextView;
    private View reboundButton;
    private View blockButton;
    private View neitherButton;
    private View stealButton;
    private View noStealButton;
    private View reboundFromBlockButton;
    private View noReboundFromBlockButton;
    private GameViewModel gameViewModel;
    private PublishSubject<GameEvent> mBackButtonPublishSubject;

    @Inject
    GameRepository gameRepository;
    @Inject
    CardillService cardillService;
    @Inject
    SyncCommentLifecycleObserver syncCommentLifecycleObserver;
    @Inject
    GameViewModelFactory gameViewModelFactory;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_game);

        getLifecycle().addObserver(syncCommentLifecycleObserver);

        GameData gameData = getIntent().getParcelableExtra(GAME_DATA);

        gameRepository.setGameData(gameData);

        team1TextView = findViewById(R.id.team_1_textview);
        team2TextView = findViewById(R.id.team_2_textview);

        teamOneRecyclerView = findViewById(R.id.team_1_recycler_view);
        teamOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        NewGamePlayerAdapter teamOneAdapter = new NewGamePlayerAdapter(gameData.getTeamOnePlayers(), Team.TEAM_ONE);
        teamOneRecyclerView.setAdapter(teamOneAdapter);

        teamTwoRecyclerView = findViewById(R.id.team_2_recycler_view);
        teamTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamTwoRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        NewGamePlayerAdapter teamTwoAdapter = new NewGamePlayerAdapter(gameData.getTeamTwoPlayers(), Team.TEAM_TWO);
        teamTwoRecyclerView.setAdapter(teamTwoAdapter);

        makeButton = findViewById(R.id.make);
        missButton = findViewById(R.id.misses);
        turnoverButton = findViewById(R.id.turnover);
        assistButton = findViewById(R.id.assist);
        noAssistButton = findViewById(R.id.no_assist);
        reboundButton = findViewById(R.id.rebound);
        neitherButton = findViewById(R.id.neither);
        blockButton = findViewById(R.id.block);
        stealButton = findViewById(R.id.steal);
        noStealButton = findViewById(R.id.no_steal);
        reboundFromBlockButton = findViewById(R.id.reboundFromBlock);
        noReboundFromBlockButton = findViewById(R.id.noReboundFromBlock);

        gameViewModel = ViewModelProviders.of(this, gameViewModelFactory).get(GameViewModel.class);

        Observable<GameEvent> clicks = getGameEventObservable();
        mBackButtonPublishSubject = PublishSubject.create();

        Observable<GameEvent> clickObservable = Observable.merge(
                clicks,
                mBackButtonPublishSubject,
                teamOneAdapter.getPlayerSelectedEvents(),
                teamTwoAdapter.getPlayerSelectedEvents());

        TextView scoreText = findViewById(R.id.score_textview);

        mPresenter = new GamePresenter(this, gameRepository, gameViewModel, clickObservable);
        gameViewModel.getGameState().observe(this, this::renderUI);
        gameViewModel.getCurrentScore().observe(this, scoreText::setText);
    }

    private Observable<GameEvent> getGameEventObservable() {
        Observable<GameEvent> makeClicks = RxView.clicks(makeButton).map(x -> new GameEvent.MakeRequested());
        Observable<GameEvent> missClicks = RxView.clicks(missButton).map(x -> new GameEvent.MissRequested());
        Observable<GameEvent> turnoverClicks = RxView.clicks(turnoverButton).map(x -> new GameEvent.TurnoverRequested());
        Observable<GameEvent> assistClicks = RxView.clicks(assistButton).map(x -> new GameEvent.AssistRequested());
        Observable<GameEvent> noAssistClicks = RxView.clicks(noAssistButton).map(x -> new GameEvent.NoAssistRequested());
        Observable<GameEvent> reboundClicks = RxView.clicks(reboundButton).map(x -> new GameEvent.ReboundRequested());
        Observable<GameEvent> neitherClicks = RxView.clicks(neitherButton).map(x -> new GameEvent.NeitherRequested());
        Observable<GameEvent> blockClicks = RxView.clicks(blockButton).map(x -> new GameEvent.BlockRequested());
        Observable<GameEvent> stealClicks = RxView.clicks(stealButton).map(x -> new GameEvent.StealRequested());
        Observable<GameEvent> noStealClicks = RxView.clicks(noStealButton).map(x -> new GameEvent.NoStealRequested());
        Observable<GameEvent> reboundFromBlockClicks = RxView.clicks(reboundFromBlockButton).map(x -> new GameEvent.ReboundFromBlockRequested());
        Observable<GameEvent> noReboundFromBlockClicks = RxView.clicks(noReboundFromBlockButton).map(x -> new GameEvent.NoReboundFromBlockRequested());

        Observable<GameEvent> mainButtonClicks = Observable.merge(
                makeClicks,
                missClicks,
                turnoverClicks
        );

        Observable<GameEvent> makeButtonClicks = Observable.merge(
                assistClicks,
                noAssistClicks
        );

        Observable<GameEvent> missButtonClicks = Observable.merge(
                blockClicks,
                reboundClicks,
                neitherClicks
        );

        Observable<GameEvent> turnoverButtonClicks = Observable.merge(
                stealClicks,
                noStealClicks
        );

        Observable<GameEvent> blockExtrasClicks = Observable.merge(
                reboundFromBlockClicks,
                noReboundFromBlockClicks
        );

        Observable<GameEvent> mainButtonExtrasClicks = Observable.merge(
                makeButtonClicks,
                missButtonClicks,
                turnoverButtonClicks
        );

        return Observable.merge(
                mainButtonExtrasClicks,
                blockExtrasClicks,
                mainButtonClicks);
    }

    @Override
    public void onBackPressed() {
        mBackButtonPublishSubject.onNext(new GameEvent.BackRequested());
    }

    private void setMainButtonsVisibility(int visibility) {
        makeButton.setVisibility(visibility);
        missButton.setVisibility(visibility);
        turnoverButton.setVisibility(visibility);
    }

    private void setPlayerListVisibility(int visibility) {
        teamOneRecyclerView.setVisibility(visibility);
        teamTwoRecyclerView.setVisibility(visibility);
        team1TextView.setVisibility(visibility);
        team2TextView.setVisibility(visibility);
    }

    private void setTeamVisibility(Team team) {
        if (team == Team.TEAM_ONE) {
            teamOneRecyclerView.setVisibility(View.VISIBLE);
            teamTwoRecyclerView.setVisibility(View.GONE);

            team1TextView.setVisibility(View.VISIBLE);
            team2TextView.setVisibility(View.GONE);
        } else {
            teamOneRecyclerView.setVisibility(View.GONE);
            teamTwoRecyclerView.setVisibility(View.VISIBLE);

            team1TextView.setVisibility(View.GONE);
            team2TextView.setVisibility(View.VISIBLE);
        }
    }

    private void setAssistButtonVisibility(int visibility) {
        assistButton.setVisibility(visibility);
        noAssistButton.setVisibility(visibility);
    }

    private void setMissExtrasButtonVisibility(int visibility) {
        blockButton.setVisibility(visibility);
        reboundButton.setVisibility(visibility);
        neitherButton.setVisibility(visibility);
    }

    private void setTurnoverExtraButtonVisibility(int visibility) {
        stealButton.setVisibility(visibility);
        noStealButton.setVisibility(visibility);
    }

    private void setBlockExtrasButtonVisibility(int visibility) {
        reboundFromBlockButton.setVisibility(visibility);
        noReboundFromBlockButton.setVisibility(visibility);
    }


    private void renderUI(GameState gameState) {
        switch (gameState) {
            case MAKE_REQUESTED:
            case MISS_REQUESTED:
            case REBOUND_REQUESTED:
            case TURNOVER_REQUESTED:
            case REBOUND_FROM_BLOCK_REQUESTED:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.VISIBLE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;

            case MAIN:
                setMainButtonsVisibility(View.VISIBLE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;

            case DETERMINE_MAKE_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.VISIBLE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;

            case DETERMINE_MISS_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.VISIBLE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;

            case DETERMINE_TURNOVER_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.VISIBLE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;
            case ASSIST_REQUESTED:
                setMainButtonsVisibility(View.GONE);
                setTeamVisibility(gameViewModel.getCurrentTeam());
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;
            case BLOCK_REQUESTED:
            case STEAL_REQUESTED:
                setMainButtonsVisibility(View.GONE);
                Team currentTeam = gameViewModel.getCurrentTeam();
                Team otherTeam = currentTeam == Team.TEAM_ONE ? Team.TEAM_TWO : Team.TEAM_ONE;
                setTeamVisibility(otherTeam);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.GONE);
                return;
            case DETERMINE_BLOCK_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                setBlockExtrasButtonVisibility(View.VISIBLE);
            default:
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_save) {
            mPresenter.saveGameRequested();
            return true;
        } else if (item.getItemId() == R.id.action_details) {
            mPresenter.detailsRequested();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameViewModel.updateScore(gameRepository.getGameStats()); //when you come back from details, update score
    }

    @Override
    public void showStatConfirmation(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Leave Game?")
                .setMessage("Do you really want to leave this game? You will lose any stats that you have already recorded.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> GameActivity.this.finish())
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void showDetails() {
        Intent intent = new Intent(this, DetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showBoxScore() {
        finish();

        Intent intent = new Intent(this, BoxScoreActivity.class);
        startActivity(intent);
    }

    @Override
    public void showGameOverConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Save Game?")
                .setMessage("Are you sure you are ready to save and end this game?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    GameData gameData = gameRepository.getGameStats();
                    Bundle params = new Bundle();
                    params.putBoolean(IS_ONLINE_KEY, isNetworkAvailable());
                    params.putSerializable(GAME_DATA, gameData);
                    mFirebaseAnalytics.logEvent("submit_game", params);
                    mPresenter.submitGameStats();
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
