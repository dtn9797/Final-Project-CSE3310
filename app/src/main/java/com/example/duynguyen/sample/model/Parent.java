package com.example.duynguyen.sample.model;

import com.example.duynguyen.sample.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Parent extends User {
    private String studentId;

    public Parent() {
    }

    public Parent(String userType, String firstName, String lastName, String phoneNum, String loginId, String pass) {
        super(userType, firstName, lastName, phoneNum, loginId, pass);
    }
    public Parent(User user){
        super(Utils.PARENT, user.getFirstName(), user.getLastName(), user.getPhoneNum(), user.getLoginId(), user.getPass());

    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
