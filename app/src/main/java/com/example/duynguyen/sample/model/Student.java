package com.example.duynguyen.sample.model;

import com.example.duynguyen.sample.utils.Utils;

import java.util.HashMap;
import java.util.List;

public class Student extends com.example.duynguyen.sample.model.User {
    HashMap<String,Evaluation> evaluations;
    HashMap<String,CloudImage> profilePics;
    HashMap<String,CloudImage> rewardPics;
    int rewardPts;


    public HashMap<String, Evaluation> getEvaluations() {
        return evaluations;
    }

    public HashMap<String, CloudImage> getProfilePics() {
        return profilePics;
    }

    public void setProfilePics(HashMap<String, CloudImage> profilePics) {
        this.profilePics = profilePics;
    }

    public HashMap<String, CloudImage> getRewardPics() {
        return rewardPics;
    }

    public void setRewardPics(HashMap<String, CloudImage> rewardPics) {
        this.rewardPics = rewardPics;
    }

    public int getRewardPts() {
        return rewardPts;
    }

    public void setRewardPts(int rewardPts) {
        this.rewardPts = rewardPts;
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
