package com.cardillsports.stattracker.game.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.data.GameStatsMapper;
import com.cardillsports.stattracker.game.data.JSONGameStats;
import com.cardillsports.stattracker.game.data.StatType;
import com.cardillsports.stattracker.game.ui.GameViewBinder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GamePresenter {
    private final GameViewBinder viewBinder;
    private final GameRepository gameRepository;
    private final CardillService mCardillService;
    private final GameViewModel viewModel;
    private final Observable<GameEvent> eventObservable;

    public GamePresenter(GameViewBinder viewBinder,
                         GameRepository gameRepository,
                         CardillService cardillService,
                         GameViewModel viewModel,
                         Observable<GameEvent> eventObservable) {
        this.viewBinder = viewBinder;

        this.gameRepository = gameRepository;
        mCardillService = cardillService;
        this.viewModel = viewModel;
        this.eventObservable = eventObservable;

        init(viewModel, eventObservable);
    }

    private void init(GameViewModel viewModel, Observable<GameEvent> eventObservable) {
        Disposable subscribe = eventObservable
                .subscribe(new Consumer<GameEvent>() {
                    @Override
                    public void accept(GameEvent gameEvent) throws Exception {
                        if (gameEvent instanceof GameEvent.MakeRequested) {
                            viewModel.setGameState(GameState.MAKE_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.PlayerSelected) {
                            GameEvent.PlayerSelected playerSelectedEvent = (GameEvent.PlayerSelected) gameEvent;

                            Player player = playerSelectedEvent.getPlayer();

                            GameState gameState = viewModel.getGameState().getValue();
                            if (gameState == null) return;

                            StatType statType = getStatKey(gameState);

                            gameRepository.incrementStat(player.id(), statType);
                            viewBinder.showStatConfirmation(player, statType.name());

                            if (gameState == GameState.ASSIST_REQUESTED
                                    || gameState == GameState.REBOUND_REQUESTED
                                    || gameState == GameState.BLOCK_REQUESTED
                                    || gameState == GameState.STEAL_REQUESTED) {
                                viewModel.setGameState(GameState.MAIN);
                            } else if (gameState == GameState.MAKE_REQUESTED) {
                                viewModel.setGameState(GameState.DETERMINE_MAKE_EXTRAS);
                            } else if (gameState == GameState.MISS_REQUESTED) {
                                viewModel.setGameState(GameState.DETERMINE_MISS_EXTRAS);
                            } else if (gameState == GameState.TURNOVER_REQUESTED) {
                                viewModel.setGameState(GameState.DETERMINE_TURNOVER_EXTRAS);
                            }
                        } else if (gameEvent instanceof GameEvent.AssistRequested) {
                            viewModel.setGameState(GameState.ASSIST_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.NoAssistRequested) {
                            viewModel.setGameState(GameState.MAIN);
                        } else if (gameEvent instanceof GameEvent.MissRequested) {
                            viewModel.setGameState(GameState.MISS_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.BlockRequested) {
                            viewModel.setGameState(GameState.BLOCK_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.ReboundRequested) {
                            viewModel.setGameState(GameState.REBOUND_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.NeitherRequested) {
                            viewModel.setGameState(GameState.MAIN);
                        }  else if (gameEvent instanceof GameEvent.TurnoverRequested) {
                            viewModel.setGameState(GameState.TURNOVER_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.StealRequested) {
                            viewModel.setGameState(GameState.STEAL_REQUESTED);
                        } else if (gameEvent instanceof GameEvent.NoStealRequested) {
                            viewModel.setGameState(GameState.MAIN);
                        }
                    }
                });
    }

    private StatType getStatKey(GameState value) {
        switch (value) {
            case ASSIST_REQUESTED:
                return StatType.ASSISTS;
            case MAKE_REQUESTED:
                return StatType.FIELD_GOAL_MADE;
            case MISS_REQUESTED:
                return StatType.FIELD_GOAL_MISSED;
            case BLOCK_REQUESTED:
                return StatType.BLOCKS;
            case STEAL_REQUESTED:
                return StatType.STEALS;
            case REBOUND_REQUESTED:
                return StatType.REBOUNDS;
            case TURNOVER_REQUESTED:
                return StatType.TURNOVERS;
        }

        throw new IllegalStateException("Invalid game state");
    }

    public void submitGameStats() {
        GameData gameData = gameRepository.getGameStats();
        JSONGameStats jsonGameStats = GameStatsMapper.transform(gameData);
        Disposable subscribe = mCardillService.saveGameStats(jsonGameStats)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> Log.d("VITHUSHAN", "submitGameStats: " + x.string()),
                        x -> Log.e("VITHUSHAN", x.getLocalizedMessage()));
    }

}