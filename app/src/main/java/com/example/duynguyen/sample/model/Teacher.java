package com.example.duynguyen.sample.model;

public class Teacher extends User {

    private String phoneNum;
    public Teacher(String name, String email, String id, String classId,String phoneNum) {
        super(name, email, id, classId, "teacher");
        this.phoneNum = phoneNum;
    }

    public Teacher(String name, String email, String classId,String phoneNum) {
        super(name, email, classId, "teacher");
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
