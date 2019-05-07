package com.cardill.sports.stattracker.consumer.article.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable{
    public String Name;
    public String Text;
    public String Date;

    protected Comment(Parcel in) {
        Name = in.readString();
        Text = in.readString();
        Date = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Text);
        parcel.writeString(Date);
    }
}