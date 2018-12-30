package com.cardill.sports.stattracker.common.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

@Entity
public class GameData implements Parcelable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "teamOnePlayers")
    @TypeConverters(PlayerTypeConverter.class)
    private List<Player> teamOnePlayers;

    @ColumnInfo(name = "teamTwoPlayers")
    @TypeConverters(PlayerTypeConverter.class)
    private List<Player> teamTwoPlayers;

    @ColumnInfo(name = "syncPending")
    private boolean syncPending;

    public GameData(
            long id,
            List<Player> teamOnePlayers,
            List<Player> teamTwoPlayers,
            boolean syncPending) {
        this.id = id;
        this.teamOnePlayers = teamOnePlayers;
        this.teamTwoPlayers = teamTwoPlayers;
        this.syncPending = syncPending;
    }

    @Ignore
    public GameData(List<Player> teamOnePlayers, List<Player> teamTwoPlayers, boolean syncPending) {
        this.teamOnePlayers = teamOnePlayers;
        this.teamTwoPlayers = teamTwoPlayers;
        this.syncPending = syncPending;
    }

    public long getId() {
        return id;
    }

    public List<Player> getTeamOnePlayers() {
        return teamOnePlayers;
    }

    public List<Player> getTeamTwoPlayers() {
        return teamTwoPlayers;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTeamOnePlayers(List<Player> teamOnePlayers) {
        this.teamOnePlayers = teamOnePlayers;
    }

    public void setTeamTwoPlaye(List<Player> teamTwoPlayers) {
        this.teamTwoPlayers = teamTwoPlayers;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    protected GameData(Parcel in) {
        id = in.readLong();

        teamOnePlayers = in.createTypedArrayList(Player.CREATOR);
        teamTwoPlayers = in.createTypedArrayList(Player.CREATOR);
        syncPending = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeTypedList(teamOnePlayers);
        dest.writeTypedList(teamTwoPlayers);
        dest.writeByte((byte) (syncPending ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameData> CREATOR = new Creator<GameData>() {
        @Override
        public GameData createFromParcel(Parcel in) {
            return new GameData(in);
        }

        @Override
        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };
}
