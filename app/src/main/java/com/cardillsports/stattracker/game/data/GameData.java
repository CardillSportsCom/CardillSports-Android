package com.cardillsports.stattracker.game.data;

import android.os.Parcelable;

import com.cardillsports.stattracker.common.data.Player;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class GameData implements Parcelable{

    public abstract List<Player> teamOnePlayers();
    public abstract List<Player> teamTwoPlayers();

    public static GameData create(List<Player> teamOne, List<Player> teamTwo) {
        return new AutoValue_GameData(teamOne, teamTwo);
    }
}
