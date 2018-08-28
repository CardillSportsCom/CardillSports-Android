package com.cardillsports.stattracker.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Player implements Parcelable{

    public abstract String firstName();
    public abstract String lastName();

    public static Player create(String firstName, String lastName) {
        return new AutoValue_Player(firstName, lastName);
    }

}
