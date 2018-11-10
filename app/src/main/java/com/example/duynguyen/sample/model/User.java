package com.example.duynguyen.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userType;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String loginId;
    private String pass;
    private String fUserId;
    private String classId;

    public User(String userType, String firstName, String lastName,String phoneNum, String loginId,String pass) {
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginId = loginId;
        this.pass = pass;
        this.phoneNum = phoneNum;
    }

    public String getUserType() {
        return userType;
    }

    public String getfUserId() {
        return fUserId;
    }

    public void setfUserId(String fUserId) {
        this.fUserId = fUserId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userType);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNum);
        dest.writeString(this.loginId);
        dest.writeString(this.pass);
        dest.writeString(this.fUserId);
        dest.writeString(this.classId);
    }

    protected User(Parcel in) {
        this.userType = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNum = in.readString();
        this.loginId = in.readString();
        this.pass = in.readString();
        this.fUserId = in.readString();
        this.classId = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}