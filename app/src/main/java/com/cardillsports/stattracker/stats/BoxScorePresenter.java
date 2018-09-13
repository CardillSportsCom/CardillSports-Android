package com.cardillsports.stattracker.stats;

import android.util.Log;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.Player;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.scores.model.boxscore.BoxScoreResponse;
import com.cardillsports.stattracker.scores.model.boxscore.PlayerStat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BoxScorePresenter {

    private final BoxScoreViewBinder viewBinder;
    private final CardillService cardillService;

    public BoxScorePresenter(BoxScoreViewBinder viewBinder, CardillService cardillService) {
        this.viewBinder = viewBinder;
        this.cardillService = cardillService;
    }

    public void loadBoxScore(String gameId) {
        Disposable mDisposable = cardillService.getBoxScore(gameId)
                .map(boxScoreResponse -> transform(boxScoreResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        viewBinder::showBoxScore,
                        throwable -> Log.e("VITHUSHAN", throwable.getLocalizedMessage()));
    }

    //TODO move this into a separate class
    private GameData transform(BoxScoreResponse boxScoreResponse) {
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();

        PlayerStat[] team1Stats = boxScoreResponse.getGameStats()[0].getPlayerStats();
        for (PlayerStat playerStat : team1Stats) {
            Player player = new Player(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team1.add(player);
        }

        PlayerStat[] team2Stats = boxScoreResponse.getGameStats()[1].getPlayerStats();
        for (PlayerStat playerStat : team2Stats) {
            Player player = new Player(
                    playerStat.getPlayer().getID(),
                    playerStat.getPlayer().getFirstName(),
                    playerStat.getPlayer().getLastName(),
                    (int)playerStat.getFGM(),
                    (int)(playerStat.getFGA() - playerStat.getFGM()),
                    (int)playerStat.getAssists(),
                    (int)playerStat.getRebounds(),
                    (int)playerStat.getBlocks(),
                    (int)playerStat.getSteals(),
                    (int)playerStat.getTurnovers());
            team2.add(player);
        }

        GameData gameData = new GameData(team1, team2, false);
        return gameData;
    }
}
