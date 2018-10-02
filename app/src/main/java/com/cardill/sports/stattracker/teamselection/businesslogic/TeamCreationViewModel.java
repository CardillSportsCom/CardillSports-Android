package com.cardill.sports.stattracker.teamselection.businesslogic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCreationViewModel extends ViewModel {

    private MutableLiveData<List<Player>> mPlayers;
    private MutableLiveData<Boolean> mIsSelectingTeamOne;
    private MutableLiveData<Boolean> misLoading;

    public TeamCreationViewModel() {
        mPlayers = new MutableLiveData<>();
        mPlayers.setValue(new ArrayList<>());

        mIsSelectingTeamOne = new MutableLiveData<>();
        mIsSelectingTeamOne.setValue(true);

        misLoading= new MutableLiveData<>();
        misLoading.setValue(true);
    }

    public MutableLiveData<List<Player>> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Player> players) {
        mPlayers.setValue(players);
    }

    public MutableLiveData<Boolean> isSelectingTeamOne() {
        return mIsSelectingTeamOne;
    }

    void isSelectingTeamOne(Boolean isSelectingTeamOne) {
        mIsSelectingTeamOne.setValue(isSelectingTeamOne);
    }

    void setLoading(boolean isLoading) {
        misLoading.setValue(isLoading);
    }

    public MutableLiveData<Boolean> isLoading() {
        return misLoading;
    }
}
