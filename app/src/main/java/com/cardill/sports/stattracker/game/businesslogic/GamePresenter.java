package com.cardill.sports.stattracker.game.businesslogic;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.game.data.GameRepository;
import com.cardill.sports.stattracker.game.data.PendingGameStat;
import com.cardill.sports.stattracker.common.data.InGameStatType;
import com.cardill.sports.stattracker.game.ui.GameViewBinder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class GamePresenter {
    private final GameViewBinder viewBinder;
    private final GameRepository gameRepository;
    private final GameViewModel viewModel;

    public GamePresenter(GameViewBinder viewBinder,
                         GameRepository gameRepository,
                         GameViewModel viewModel,
                         Observable<GameEvent> eventObservable) {
        this.viewBinder = viewBinder;
        this.gameRepository = gameRepository;
        this.viewModel = viewModel;

        init(viewModel, eventObservable);
    }

    private void init(GameViewModel viewModel, Observable<GameEvent> eventObservable) {
        Disposable subscribe = eventObservable
                .subscribe(gameEvent -> {
                    if (gameEvent instanceof GameEvent.MakeOnePointRequested) {
                        viewModel.setGameState(GameState.MAKE_ONE_POINT_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.MakeTwoPointRequested) {
                        viewModel.setGameState(GameState.MAKE_TWO_POINT_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.PlayerSelected) {
                        GameEvent.PlayerSelected playerSelectedEvent = (GameEvent.PlayerSelected) gameEvent;

                        Player player = playerSelectedEvent.getPlayer();

                        viewModel.setCurrentTeam(playerSelectedEvent.getTeam());

                        GameState gameState = viewModel.getGameState().getValue();
                        if (gameState == null) return;

                        InGameStatType inGameStatType = getStatKey(gameState);

                        if (gameState == GameState.ASSIST_REQUESTED
                                || gameState == GameState.REBOUND_REQUESTED
                                || gameState == GameState.REBOUND_FROM_BLOCK_REQUESTED
                                || gameState == GameState.BLOCK_REQUESTED
                                || gameState == GameState.STEAL_REQUESTED) {
                            if (gameState == GameState.ASSIST_REQUESTED) {
                                PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                                gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                                gameRepository.incrementStat(player.id(), inGameStatType);
                                viewModel.updateScore(gameRepository.getGameStats());
                                viewBinder.showStatConfirmation(player.firstName() + " assists to " + latestPendingGameStat.getPlayer().firstName());
                            } else if (gameState == GameState.REBOUND_REQUESTED) {
                                PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                                gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                                gameRepository.incrementStat(player.id(), inGameStatType);
                                viewBinder.showStatConfirmation(player.firstName() + " rebounded " + latestPendingGameStat.getPlayer().firstName() + "'s missed shot");
                            } else if (gameState == GameState.BLOCK_REQUESTED) {
                                gameRepository.incrementPendingStat(player, inGameStatType);
                                viewModel.setGameState(GameState.DETERMINE_BLOCK_EXTRAS);
                            } else if (gameState == GameState.STEAL_REQUESTED) {
                                PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                                gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                                gameRepository.incrementStat(player.id(), inGameStatType);
                                viewBinder.showStatConfirmation(player.firstName() + " stole from " + latestPendingGameStat.getPlayer().firstName());
                            } else if (gameState == GameState.REBOUND_FROM_BLOCK_REQUESTED) {
                                PendingGameStat miss = gameRepository.getLatestPendingStat();
                                PendingGameStat block = gameRepository.getLatestPendingStat();
                                gameRepository.incrementStat(miss.getPlayer().id(), miss.getInGameStatType());
                                gameRepository.incrementStat(block.getPlayer().id(), block.getInGameStatType());
                                gameRepository.incrementStat(player.id(), inGameStatType);
                                viewBinder.showStatConfirmation(miss.getPlayer().firstName() + " missed shot was blocked by "
                                        + block.getPlayer().firstName() + " and rebounded by "
                                        + player.firstName());
                            }

                            if (gameState != GameState.BLOCK_REQUESTED) {
                                viewModel.setGameState(GameState.MAIN);
                            }
                        } else if (gameState == GameState.MAKE_ONE_POINT_REQUESTED ||
                                gameState == GameState.MAKE_TWO_POINT_REQUESTED) {
                            gameRepository.incrementPendingStat(player, inGameStatType);
                            viewModel.setGameState(GameState.DETERMINE_MAKE_EXTRAS);
                        } else if (gameState == GameState.MISS_REQUESTED) {
                            gameRepository.incrementPendingStat(player, inGameStatType);
                            viewModel.setGameState(GameState.DETERMINE_MISS_EXTRAS);
                        } else if (gameState == GameState.TURNOVER_REQUESTED) {
                            gameRepository.incrementPendingStat(player, inGameStatType);
                            viewModel.setGameState(GameState.DETERMINE_TURNOVER_EXTRAS);
                        }
                    } else if (gameEvent instanceof GameEvent.AssistRequested) {
                        viewModel.setGameState(GameState.ASSIST_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.NoAssistRequested) {
                        PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                        gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                        viewBinder.showStatConfirmation(latestPendingGameStat.getPlayer().firstName() + " made shot");
                        viewModel.updateScore(gameRepository.getGameStats());
                        viewModel.setGameState(GameState.MAIN);
                    } else if (gameEvent instanceof GameEvent.MissRequested) {
                        viewModel.setGameState(GameState.MISS_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.BlockRequested) {
                        viewModel.setGameState(GameState.BLOCK_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.ReboundFromBlockRequested) {
                        viewModel.setGameState(GameState.REBOUND_FROM_BLOCK_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.NoReboundFromBlockRequested) {
                        PendingGameStat miss = gameRepository.getLatestPendingStat();
                        PendingGameStat block = gameRepository.getLatestPendingStat();
                        gameRepository.incrementStat(miss.getPlayer().id(), miss.getInGameStatType());
                        gameRepository.incrementStat(block.getPlayer().id(), block.getInGameStatType());
                        viewBinder.showStatConfirmation(block.getPlayer().firstName() + " blocked " + miss.getPlayer().firstName());
                        viewModel.setGameState(GameState.MAIN);
                    } else if (gameEvent instanceof GameEvent.ReboundRequested) {
                        viewModel.setGameState(GameState.REBOUND_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.NeitherRequested) {
                        PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                        gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                        viewBinder.showStatConfirmation(latestPendingGameStat.getPlayer().firstName() + " missed shot");
                        viewModel.setGameState(GameState.MAIN);
                    } else if (gameEvent instanceof GameEvent.TurnoverRequested) {
                        viewModel.setGameState(GameState.TURNOVER_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.StealRequested) {
                        viewModel.setGameState(GameState.STEAL_REQUESTED);
                    } else if (gameEvent instanceof GameEvent.NoStealRequested) {

                        PendingGameStat latestPendingGameStat = gameRepository.getLatestPendingStat();
                        gameRepository.incrementStat(latestPendingGameStat.getPlayer().id(), latestPendingGameStat.getInGameStatType());
                        viewBinder.showStatConfirmation(latestPendingGameStat.getPlayer().firstName() + " turnover");

                        viewModel.setGameState(GameState.MAIN);
                    } else if (gameEvent instanceof GameEvent.BackRequested) {
                        if (viewModel.getGameState().getValue() == GameState.MAKE_ONE_POINT_REQUESTED ||
                                viewModel.getGameState().getValue() == GameState.MAKE_TWO_POINT_REQUESTED) {
                            viewModel.setGameState(GameState.MAIN);
                        } else if (viewModel.getGameState().getValue() == GameState.DETERMINE_MAKE_EXTRAS) {
                            PendingGameStat pendingGameStat = gameRepository.removeFromQueue();
                            if (pendingGameStat.getInGameStatType() == InGameStatType.MAKE_ONE_POINT) {
                                viewModel.setGameState(GameState.MAKE_ONE_POINT_REQUESTED);
                            } else if (pendingGameStat.getInGameStatType() == InGameStatType.MAKE_TWO_POINT) {
                                viewModel.setGameState(GameState.MAKE_TWO_POINT_REQUESTED);
                            }
                        } else if (viewModel.getGameState().getValue() == GameState.ASSIST_REQUESTED) {
                            viewModel.setGameState(GameState.DETERMINE_MAKE_EXTRAS);
                        } else if (viewModel.getGameState().getValue() == GameState.MISS_REQUESTED) {
                            viewModel.setGameState(GameState.MAIN);
                        } else if (viewModel.getGameState().getValue() == GameState.DETERMINE_MISS_EXTRAS) {
                            gameRepository.removeFromQueue();
                            viewModel.setGameState(GameState.MISS_REQUESTED);
                        } else if (viewModel.getGameState().getValue() == GameState.REBOUND_REQUESTED) {
                            viewModel.setGameState(GameState.DETERMINE_MISS_EXTRAS);
                        } else if (viewModel.getGameState().getValue() == GameState.BLOCK_REQUESTED) {
                            viewModel.setGameState(GameState.DETERMINE_MISS_EXTRAS);
                        } else if (viewModel.getGameState().getValue() == GameState.TURNOVER_REQUESTED) {
                            viewModel.setGameState(GameState.MAIN);
                        } else if (viewModel.getGameState().getValue() == GameState.DETERMINE_TURNOVER_EXTRAS) {
                            gameRepository.removeFromQueue();
                            viewModel.setGameState(GameState.TURNOVER_REQUESTED);
                        } else if (viewModel.getGameState().getValue() == GameState.STEAL_REQUESTED) {
                            viewModel.setGameState(GameState.DETERMINE_TURNOVER_EXTRAS);
                        } else if (viewModel.getGameState().getValue() == GameState.MAIN) {
                            viewBinder.showExitConfirmation();
                        } else if (viewModel.getGameState().getValue() == GameState.DETERMINE_BLOCK_EXTRAS) {
                            gameRepository.removeFromQueue();
                            viewModel.setCurrentTeam(viewModel.getCurrentTeam() == Team.TEAM_ONE ? Team.TEAM_TWO : Team.TEAM_ONE);
                            viewModel.setGameState(GameState.BLOCK_REQUESTED);
                        }
                    }
                });
    }

    private InGameStatType getStatKey(GameState value) {
        switch (value) {
            case ASSIST_REQUESTED:
                return InGameStatType.AST;
            case MAKE_ONE_POINT_REQUESTED:
                return InGameStatType.MAKE_ONE_POINT;
            case MAKE_TWO_POINT_REQUESTED:
                return InGameStatType.MAKE_TWO_POINT;
            case MISS_REQUESTED:
                return InGameStatType.MISSES;
            case BLOCK_REQUESTED:
                return InGameStatType.BLK;
            case STEAL_REQUESTED:
                return InGameStatType.STL;
            case REBOUND_REQUESTED:
                return InGameStatType.REB;
            case TURNOVER_REQUESTED:
                return InGameStatType.TO;
            case REBOUND_FROM_BLOCK_REQUESTED:
                return InGameStatType.REB;
        }

        throw new IllegalStateException("Invalid game state");
    }

    public void submitGameStats() {
        GameData gameData = gameRepository.getGameStats();
        viewModel.saveGame(gameData);
        boxScoreRequested();
    }

    public void detailsRequested() {
        viewBinder.showDetails();
    }

    private void boxScoreRequested() {
        viewBinder.showBoxScore();
    }

    public void saveGameRequested() {
        viewBinder.showGameOverConfirmation();
    }

    public void addPlayer(Player player, boolean isTeamOne) {
        GameData gameStats = gameRepository.getGameStats();
        if (isTeamOne) {
            List<Player> teamOnePlayers = gameStats.getTeamOnePlayers();
            teamOnePlayers.add(player);
        } else {
            List<Player> teamTwoPlayers = gameStats.getTeamTwoPlayers();
            teamTwoPlayers.add(player);
        }
    }

    public void addPlayerRequested() {
        viewBinder.showPlayerList();
    }
}