package com.cardill.sports.stattracker.teamselection.businesslogic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectionViewModel extends ViewModel {

    private MutableLiveData<List<Player>> mPlayers;
    private MutableLiveData<Boolean> mSelectingTeam1;
    private MutableLiveData<Boolean> misLoading;

    public TeamSelectionViewModel() {
        mPlayers = new MutableLiveData<>();
        mPlayers.setValue(new ArrayList<>());

        mSelectingTeam1 = new MutableLiveData<>();
        mSelectingTeam1.setValue(true);

        misLoading= new MutableLiveData<>();
        misLoading.setValue(true);
    }

    public MutableLiveData<List<Player>> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Player> players) {
        mPlayers.setValue(players);
    }

    public MutableLiveData<Boolean> getSelectingTeam1() {
        return mSelectingTeam1;
    }

    public void setSelectingTeam1(Boolean mSelectingTeam1) {
        this.mSelectingTeam1.setValue(mSelectingTeam1);
    }

    public void setLoading(boolean isLoading) {
        misLoading.setValue(isLoading);
    }

    public MutableLiveData<Boolean> isLoading() {
        return misLoading;
    }
}
