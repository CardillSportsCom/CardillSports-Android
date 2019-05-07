package com.cardill.sports.stattracker.creator.game.businesslogic;

import com.cardill.sports.stattracker.common.data.Player;

public interface GameEvent {
    class MakeOnePointRequested implements GameEvent {}
    class MakeTwoPointRequested implements GameEvent {}

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

    class ReboundFromBlockRequested implements GameEvent {}

    class NoReboundFromBlockRequested implements GameEvent {}
}
