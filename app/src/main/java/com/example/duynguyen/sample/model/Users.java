package com.example.duynguyen.sample.model;

public class Users {
    private String type;
    private String fname;
    private String lname;
    private String pass;
    private String email;
    private String phone;

    public Users(String type, String fname, String lname, String pass, String email, String phone){
        this.type = type;
        this.fname = fname;
        this.lname = lname;
        this.pass = pass;
        this.email = email;
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public String getFname(){ return fname; }
    public String getLname(){ return lname; }
    public String getPass(){
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFname(String fname){
        this.fname = fname;
    }
    public void setLname(String lname){ this.lname = lname;}

    public void setPass(String pass){
        this.pass = pass;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
