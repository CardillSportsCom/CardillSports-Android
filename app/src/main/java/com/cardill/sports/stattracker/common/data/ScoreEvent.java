package com.cardill.sports.stattracker.common.data;

import com.cardill.sports.stattracker.gamedays.data.Game;
import com.cardill.sports.stattracker.gamedays.data.GameDay;

/**
 * User triggered events.
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

    class GameSelected {
        private Game gameDay;

        public GameSelected(Game gameDay) {
            this.gameDay = gameDay;
        }

        public Game getGameDay() {
            return gameDay;
        }
    }
}
