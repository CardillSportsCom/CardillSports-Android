package com.cardill.sports.stattracker.common.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ConsumerGameData implements Parcelable, Serializable {

    public static ConsumerGameData getExample() {
        ConsumerPlayer player = ConsumerPlayer.create("dfs", "VITHU", "NAMA");;

        List<ConsumerPlayer> team1 = new ArrayList<ConsumerPlayer>();
        team1.add(player);
        ConsumerPlayer player2 = ConsumerPlayer.create("hgh", "ANU", "Ranjan");
        List<ConsumerPlayer> team2 = new ArrayList<ConsumerPlayer>();
        team2.add(player2);
        ConsumerGameData gameData = new ConsumerGameData(1, team1, team2, false);
        return gameData;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "teamOnePlayers")
    @TypeConverters(PlayerTypeConverter.class)
    private List<ConsumerPlayer> teamOnePlayers;

    @ColumnInfo(name = "teamTwoPlayers")
    @TypeConverters(PlayerTypeConverter.class)
    private List<ConsumerPlayer> teamTwoPlayers;

    @ColumnInfo(name = "syncPending")
    private boolean syncPending;

    public ConsumerGameData(
            long id,
            List<ConsumerPlayer> teamOnePlayers,
            List<ConsumerPlayer> teamTwoPlayers,
            boolean syncPending) {
        this.id = id;
        this.teamOnePlayers = teamOnePlayers;
        this.teamTwoPlayers = teamTwoPlayers;
        this.syncPending = syncPending;
    }

    @Ignore
    public ConsumerGameData(List<ConsumerPlayer> teamOnePlayers, List<ConsumerPlayer> teamTwoPlayers, boolean syncPending) {
        this.teamOnePlayers = teamOnePlayers;
        this.teamTwoPlayers = teamTwoPlayers;
        this.syncPending = syncPending;
    }

    public long getId() {
        return id;
    }

    public List<ConsumerPlayer> getTeamOnePlayers() {
        return teamOnePlayers;
    }

    public List<ConsumerPlayer> getTeamTwoPlayers() {
        return teamTwoPlayers;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTeamOnePlayers(List<ConsumerPlayer> teamOnePlayers) {
        this.teamOnePlayers = teamOnePlayers;
    }

    public void setTeamTwoPlaye(List<ConsumerPlayer> teamTwoPlayers) {
        this.teamTwoPlayers = teamTwoPlayers;
    }

    public void setSyncPending(boolean syncPending) {
        this.syncPending = syncPending;
    }

    protected ConsumerGameData(Parcel in) {
        id = in.readLong();

        teamOnePlayers = in.createTypedArrayList(ConsumerPlayer.CREATOR);
        teamTwoPlayers = in.createTypedArrayList(ConsumerPlayer.CREATOR);
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

    public static final Creator<ConsumerGameData> CREATOR = new Creator<ConsumerGameData>() {
        @Override
        public ConsumerGameData createFromParcel(Parcel in) {
            return new ConsumerGameData(in);
        }

        @Override
        public ConsumerGameData[] newArray(int size) {
            return new ConsumerGameData[size];
        }
    };
}
