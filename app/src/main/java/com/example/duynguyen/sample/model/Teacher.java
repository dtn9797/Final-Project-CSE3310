package com.example.duynguyen.sample.model;

import android.os.Parcel;

import com.example.duynguyen.sample.utils.Utils;

public class Teacher extends User {
    public Teacher(User user) {
        super(Utils.TEACHER,user.getFirstName(),user.getLastName(),user.getPhoneNum(),user.getLoginId(),user.getPass());
    }
}
