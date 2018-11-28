package com.cardill.sports.stattracker.article.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CardillContent implements Parcelable {
    public String _id;
    public String createdAt;
    public String Name;
    public String Image;
    public com.cardill.sports.stattracker.article.data.Creator Creator;
    public String Body;
    public List<Comment> Comments;
    public float Rating;

    protected CardillContent(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        Name = in.readString();
        Image = in.readString();
        Creator = Creator.CREATOR.createFromParcel(in);
        Body = in.readString();
        Rating = in.readFloat();
    }

    public static final Creator<CardillContent> CREATOR = new Creator<CardillContent>() {
        @Override
        public CardillContent createFromParcel(Parcel in) {
            return new CardillContent(in);
        }

        @Override
        public CardillContent[] newArray(int size) {
            return new CardillContent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(Name);
        dest.writeString(Image);
        Creator.writeToParcel(dest, i);
        dest.writeString(Body);
        dest.writeTypedList(Comments);
        dest.writeFloat(Rating);
    }
}