package com.cardill.sports.stattracker.teamselection.businesslogic;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamselection.data.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamSelectionViewModel extends ViewModel {

    private MutableLiveData<List<Team>> mTeams;
    private MutableLiveData<Boolean> mIsSelectingTeamOne;
    private MutableLiveData<Boolean> misLoading;

    public TeamSelectionViewModel() {
        mTeams = new MutableLiveData<>();
        mTeams.setValue(new ArrayList<>());

        mIsSelectingTeamOne = new MutableLiveData<>();
        mIsSelectingTeamOne.setValue(true);

        misLoading= new MutableLiveData<>();
        misLoading.setValue(true);
    }

    public MutableLiveData<List<Team>> getTeams() {
        return mTeams;
    }

    public void setTeams(List<Team> teams) {
        mTeams.setValue(teams);
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
