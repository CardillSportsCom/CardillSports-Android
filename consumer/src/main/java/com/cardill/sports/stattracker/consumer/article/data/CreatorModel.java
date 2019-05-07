package com.cardill.sports.stattracker.consumer.article.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CreatorModel implements Parcelable {
    public CreatorModelId _id;
    public int articleCount;

    protected CreatorModel(Parcel in) {
        _id = CreatorModelId.CREATOR.createFromParcel(in);
        articleCount = in.readInt();
    }

    public static final Creator<CreatorModel> CREATOR = new Creator<CreatorModel>() {
        @Override
        public CreatorModel createFromParcel(Parcel in) {
            return new CreatorModel(in);
        }

        @Override
        public CreatorModel[] newArray(int size) {
            return new CreatorModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        _id.writeToParcel(parcel, i);
        parcel.writeInt(articleCount);
    }
}