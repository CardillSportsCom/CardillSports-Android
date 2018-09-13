package com.cardillsports.stattracker.stats;

import com.cardillsports.stattracker.scores.model.Game;
import com.cardillsports.stattracker.scores.model.GameDay;

/**
 * Created by vithushan on 9/12/18.
 */

public interface ScoreEvent {
    class DateSelected implements ScoreEvent{
        private final GameDay gameDay;

        public DateSelected(GameDay gameDay) {
            this.gameDay = gameDay;
        }

        public GameDay getGameDay() {
            return gameDay;
        }
    }

    public class GameSelected {
        private Game gameDay;

        public GameSelected(Game gameDay) {
            this.gameDay = gameDay;
        }

        public Game getGameDay() {
            return gameDay;
        }
    }
}
