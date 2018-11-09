package com.example.duynguyen.sample.model;

public class User {
    private String name;
    private String email;
    private String id;
    private String classId;
    private String type;

    public User(String name, String email, String id, String classId,String type) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.classId = classId;
        this.type = type;
    }

    public User(String name, String email, String classId, String type) {
        this.name = name;
        this.email = email;
        this.classId = classId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
