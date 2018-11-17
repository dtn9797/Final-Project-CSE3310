package com.example.duynguyen.sample.Model;

import java.util.List;

public class MessageChannel {
    private String parentName;
    private String studentName;
    private String teacherName;
    private String id;


    public MessageChannel(String parentName, String studentName, String teacherName,String id) {
        this.parentName = parentName;
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageChannel() {
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
