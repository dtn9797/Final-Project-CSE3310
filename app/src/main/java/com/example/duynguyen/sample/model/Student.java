package com.example.duynguyen.sample.model;

import com.example.duynguyen.sample.utils.Utils;

import java.util.HashMap;
import java.util.List;

public class Student extends com.example.duynguyen.sample.model.User {
    HashMap<String,Evaluation> evaluations;


    public HashMap<String, Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(HashMap<String, Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Student() {
    }

    public Student(String firstName, String lastName, String phoneNum, String loginId, String pass) {
        super(Utils.STUDENT, firstName, lastName, phoneNum, loginId, pass);
    }
    public Student(com.example.duynguyen.sample.model.User user){
        super(Utils.STUDENT,user.getFirstName(),user.getLastName(),user.getPhoneNum(),user.getLoginId(),user.getPass());
    }
}
