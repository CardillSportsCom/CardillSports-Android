package com.cardillsports.stattracker.common.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Player implements Parcelable{

    public abstract String id();

    public abstract String firstName();
    public abstract String lastName();

    public abstract int fieldGoalMade();
    public abstract int fieldGoalMissed();
    public abstract int assists();
    public abstract int rebounds();
    public abstract int blocks();
    public abstract int steals();
    public abstract int turnovers();
    public abstract Player.Builder toBuilder();

    public static Player create(String id, String firstName, String lastName) {
        return builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0)
                .build();
    }

    static Builder builder() {
        return new AutoValue_Player.Builder()
                .fieldGoalMade(0)
                .fieldGoalMissed(0)
                .assists(0)
                .rebounds(0)
                .blocks(0)
                .steals(0)
                .turnovers(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder firstName(String firstName);

        public abstract Builder lastName(String lastName);

        public abstract Builder fieldGoalMade(int fieldGoalMade);

        public abstract Builder fieldGoalMissed(int fieldGoalMissed);

        public abstract Builder assists(int rebounds);

        public abstract Builder rebounds(int rebounds);

        public abstract Builder blocks(int blocks);

        public abstract Builder steals(int steals);

        public abstract Builder turnovers(int turnovers);

        public abstract Player build();
    }
}
