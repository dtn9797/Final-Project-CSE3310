package com.example.duynguyen.sample.model;

public class Users {
    private String type;
    private String key;
    private String fname;
    private String lname;
    private String pass;
    private String email;
    private String phone;
    private String student;
    private String parent;

public Users (){

}


    public Users(String type, String key, String fname, String lname, String pass, String email, String phone, String student, String parent){
        this.type = type;
        this.key = key;
        this.fname = fname;
        this.lname = lname;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
        this.student = student;
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getFname(){
        return fname;
    }
    public String getLname(){
        return lname;
    }
    public String getPass(){
        return pass;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getParent() {
        return parent;
    }

    public String getStudent() {
        return student;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFname(String fname){
        this.fname = fname;
    }
    public void setLname(String lname){
        this.lname = lname;
    }

    public void setPass(String pass){
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
