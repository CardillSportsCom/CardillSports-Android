package com.cardill.sports.stattracker.consumer.article.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CreatorModelId implements Parcelable {
    public String firstName;
    public String lastName;
    public String userPicture;

    protected CreatorModelId(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        userPicture = in.readString();
    }

    public static final Creator<CreatorModelId> CREATOR = new Creator<CreatorModelId>() {
        @Override
        public CreatorModelId createFromParcel(Parcel in) {
            return new CreatorModelId(in);
        }

        @Override
        public CreatorModelId[] newArray(int size) {
            return new CreatorModelId[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(userPicture);
    }
}