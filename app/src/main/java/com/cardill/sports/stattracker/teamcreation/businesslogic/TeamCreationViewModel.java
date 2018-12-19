package com.cardill.sports.stattracker.teamcreation.businesslogic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCreationViewModel extends ViewModel {

    private MutableLiveData<List<Player>> mPlayers;
    private MutableLiveData<Boolean> misLoading;

    public TeamCreationViewModel() {
        mPlayers = new MutableLiveData<>();
        mPlayers.setValue(new ArrayList<>());

        misLoading= new MutableLiveData<>();
        misLoading.setValue(true);
    }

    public MutableLiveData<List<Player>> getPlayers() {
        return mPlayers;
    }

    public void setPlayers(List<Player> players) {
        mPlayers.setValue(players);
    }

    void setLoading(boolean isLoading) {
        misLoading.setValue(isLoading);
    }

    public MutableLiveData<Boolean> isLoading() {
        return misLoading;
    }
}
