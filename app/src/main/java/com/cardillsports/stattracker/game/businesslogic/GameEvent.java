package com.cardillsports.stattracker.game.businesslogic;

import com.cardillsports.stattracker.common.data.Player;

public interface GameEvent {
    class MakeRequested implements GameEvent {}

    class PlayerSelected implements GameEvent {
        private final Player player;
        private final Team team;

        PlayerSelected(Team team, Player player) {
            this.team = team;
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public Team getTeam() {
            return team;
        }
    }

    class MissRequested implements GameEvent {}
    class TurnoverRequested implements GameEvent {}
    class AssistRequested implements GameEvent {}
    class NoAssistRequested implements GameEvent {}

    class ReboundRequested implements GameEvent {}
    class NeitherRequested implements GameEvent {}
    class BlockRequested implements GameEvent {}

    class StealRequested implements GameEvent {}
    class NoStealRequested implements GameEvent {}

    class BackRequested implements GameEvent {}
}
