package com.cardillsports.stattracker.game.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.game.businesslogic.GameEvent;
import com.cardillsports.stattracker.game.businesslogic.GamePresenter;
import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.businesslogic.GameState;
import com.cardillsports.stattracker.game.businesslogic.GameViewModel;
import com.cardillsports.stattracker.game.businesslogic.NewGamePlayerAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cardillsports.stattracker.main.ui.MainActivity.GAME_DATA;

public class GameActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://api-cardillsports-st.herokuapp.com";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CardillService cardillService = retrofit.create(CardillService.class);

        GameData gameData = getIntent().getParcelableExtra(GAME_DATA);

        GameRepository gameRepository = new GameRepository(gameData);

        team1TextView = findViewById(R.id.team_1_textview);
        team2TextView = findViewById(R.id.team_2_textview);

        teamOneRecyclerView = findViewById(R.id.team_1_recycler_view);
        teamOneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //GamePlayerAdapter teamOneAdapter = new GamePlayerAdapter(gameData.teamOnePlayers(), gameRepository);
        NewGamePlayerAdapter teamOneAdapter = new NewGamePlayerAdapter(gameData.teamOnePlayers());
        teamOneRecyclerView.setAdapter(teamOneAdapter);

        teamTwoRecyclerView = findViewById(R.id.team_2_recycler_view);
        teamTwoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teamTwoRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //GamePlayerAdapter teamTwoAdapter = new GamePlayerAdapter(gameData.teamTwoPlayers(), gameRepository);
        NewGamePlayerAdapter teamTwoAdapter = new NewGamePlayerAdapter(gameData.teamTwoPlayers());
        teamTwoRecyclerView.setAdapter(teamTwoAdapter);

        makeButton = findViewById(R.id.make);
        Observable<GameEvent> makeClicks = RxView.clicks(makeButton).map(x -> new GameEvent.MakeRequested());

        missButton = findViewById(R.id.misses);
        Observable<GameEvent> missClicks = RxView.clicks(missButton).map(x -> new GameEvent.MissRequested());

        turnoverButton = findViewById(R.id.turnover);
        Observable<GameEvent> turnoverClicks = RxView.clicks(turnoverButton).map(x -> new GameEvent.TurnoverRequested());

        assistButton = findViewById(R.id.assist);
        Observable<GameEvent> assistClicks = RxView.clicks(assistButton).map(x -> new GameEvent.AssistRequested());

        noAssistButton = findViewById(R.id.no_assist);
        Observable<GameEvent> noAssistClicks = RxView.clicks(noAssistButton).map(x -> new GameEvent.NoAssistRequested());

        reboundButton = findViewById(R.id.rebound);
        Observable<GameEvent> reboundClicks = RxView.clicks(reboundButton).map(x -> new GameEvent.ReboundRequested());

        neitherButton = findViewById(R.id.neither);
        Observable<GameEvent> neitherClicks = RxView.clicks(neitherButton).map(x -> new GameEvent.NeitherRequested());

        blockButton = findViewById(R.id.block);
        Observable<GameEvent> blockClicks = RxView.clicks(blockButton).map(x -> new GameEvent.BlockRequested());

        stealButton = findViewById(R.id.steal);
        Observable<GameEvent> stealClicks = RxView.clicks(stealButton).map(x -> new GameEvent.StealRequested());

        noStealButton = findViewById(R.id.no_steal);
        Observable<GameEvent> noStealClicks = RxView.clicks(noStealButton).map(x -> new GameEvent.NoStealRequested());

        GameViewModel model = ViewModelProviders.of(this).get(GameViewModel.class);

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

        Observable<GameEvent> clicks = Observable.merge(
                mainButtonClicks,
                makeButtonClicks,
                missButtonClicks,
                turnoverButtonClicks
        );

        Observable<GameEvent> clickObservable = Observable.merge(
                clicks,
                teamOneAdapter.getPlayerSelectedEvents(),
                teamTwoAdapter.getPlayerSelectedEvents());

        mPresenter = new GamePresenter(gameRepository, cardillService, model, clickObservable);
        model.getGameState().observe(this, this::renderUI);
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


    private void renderUI(GameState gameState) {
        switch (gameState) {
            case MAKE_REQUESTED:
            case MISS_REQUESTED:
            case ASSIST_REQUESTED:
            case BLOCK_REQUESTED:
            case REBOUND_REQUESTED:
            case TURNOVER_REQUESTED:
            case STEAL_REQUESTED:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.VISIBLE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                return;

            case MAIN:
                setMainButtonsVisibility(View.VISIBLE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                return;

            case DETERMINE_MAKE_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.VISIBLE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.GONE);
                return;

            case DETERMINE_MISS_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.VISIBLE);
                setTurnoverExtraButtonVisibility(View.GONE);
                return;

            case DETERMINE_TURNOVER_EXTRAS:
                setMainButtonsVisibility(View.GONE);
                setPlayerListVisibility(View.GONE);
                setAssistButtonVisibility(View.GONE);
                setMissExtrasButtonVisibility(View.GONE);
                setTurnoverExtraButtonVisibility(View.VISIBLE);
                return;
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
            mPresenter.submitGameStats();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
