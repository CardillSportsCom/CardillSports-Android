package com.cardillsports.stattracker.game.businesslogic;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.data.GameStatsMapper;
import com.cardillsports.stattracker.game.data.JSONGameStats;
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
                            String statName = getStatName(viewModel.getGameState().getValue());
                            viewBinder.showStatConfirmation(player, statName);

                            if (viewModel.getGameState().getValue() == GameState.ASSIST_REQUESTED
                                    || viewModel.getGameState().getValue() == GameState.REBOUND_REQUESTED
                                    || viewModel.getGameState().getValue() == GameState.BLOCK_REQUESTED
                                    || viewModel.getGameState().getValue() == GameState.STEAL_REQUESTED) {
                                viewModel.setGameState(GameState.MAIN);
                            } else if (viewModel.getGameState().getValue() == GameState.MAKE_REQUESTED) {
                                viewModel.setGameState(GameState.DETERMINE_MAKE_EXTRAS);
                            } else if (viewModel.getGameState().getValue() == GameState.MISS_REQUESTED) {
                                viewModel.setGameState(GameState.DETERMINE_MISS_EXTRAS);
                            } else if (viewModel.getGameState().getValue() == GameState.TURNOVER_REQUESTED) {
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

    private String getStatName(GameState value) {
        switch (value) {
            case ASSIST_REQUESTED:
                return "ASSIST";
            case MAKE_REQUESTED:
                return "MADE";
            case MISS_REQUESTED:
                return "MISS";
            case BLOCK_REQUESTED:
                return "BLOCK";
            case STEAL_REQUESTED:
                return "STEAL";
            case REBOUND_REQUESTED:
                return "REBOUND";
            case TURNOVER_REQUESTED:
                return "TURNOVER";
            default:
                return "ERROR";

        }
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