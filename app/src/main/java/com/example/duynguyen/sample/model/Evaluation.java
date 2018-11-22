package com.example.duynguyen.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

public  class Evaluation implements Parcelable {
    private String behavior;
    private String comment;
    private String date;

    public Evaluation(String behavior, String comment, String date) {
        this.behavior = behavior;
        this.comment = comment;
        this.date = date;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Evaluation() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.behavior);
        dest.writeString(this.comment);
        dest.writeString(this.date);
    }

    protected Evaluation(Parcel in) {
        this.behavior = in.readString();
        this.comment = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Evaluation> CREATOR = new Parcelable.Creator<Evaluation>() {
        @Override
        public Evaluation createFromParcel(Parcel source) {
            return new Evaluation(source);
        }

        @Override
        public Evaluation[] newArray(int size) {
            return new Evaluation[size];
        }
    };
}
