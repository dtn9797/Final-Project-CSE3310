package com.example.duynguyen.sample.utils;

public class Utils {
	
	//Email Validation pattern
	public static final String emailRegEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
	//Username Validation pattern
	public static final String userNameRegEx = "[a-zA-Z0-9._]";

	//Fragments Tags
	public static final String Login_Fragment = "Login_Fragment";
	public static final String SignUp_Fragment = "SignUp_Fragment";
	public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

	//User Types
    public static final String PARENT= "parent";
    public static final String TEACHER= "teacher";
    public static final String STUDENT= "student";



}
