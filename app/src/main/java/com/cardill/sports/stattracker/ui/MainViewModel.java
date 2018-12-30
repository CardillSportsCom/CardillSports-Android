package com.cardill.sports.stattracker.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> title;

    public MainViewModel() {
        title = new MutableLiveData<>();
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title.setValue(newTitle);
    }
}
