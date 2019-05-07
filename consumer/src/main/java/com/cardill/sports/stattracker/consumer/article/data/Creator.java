package com.cardill.sports.stattracker.consumer.article.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Creator implements Parcelable {
    public String userPicture;
    public String name;

    protected Creator(Parcel in) {
        userPicture = in.readString();
        name = in.readString();
    }

    public static final Creator<com.cardill.sports.stattracker.consumer.article.data.Creator> CREATOR = new Creator<com.cardill.sports.stattracker.consumer.article.data.Creator>() {
        @Override
        public com.cardill.sports.stattracker.consumer.article.data.Creator createFromParcel(Parcel in) {
            return new com.cardill.sports.stattracker.consumer.article.data.Creator(in);
        }

        @Override
        public com.cardill.sports.stattracker.consumer.article.data.Creator[] newArray(int size) {
            return new com.cardill.sports.stattracker.consumer.article.data.Creator[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userPicture);
        parcel.writeString(name);
    }
}