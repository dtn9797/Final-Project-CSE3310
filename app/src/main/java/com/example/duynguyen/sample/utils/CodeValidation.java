package com.example.duynguyen.sample.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.duynguyen.sample.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CodeValidation {

    public static void checkClassId(String id,final User user,final DatabaseReference dataBase, final Context context) {
        //Check id format
        boolean formatChecker = Pattern.matches("myStudent[0-9]{5,5}$", id);
        if (formatChecker) {
            final String classId = id;
            //check if classId exists in the database
            dataBase.child("classes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(classId).exists()) {
                        //Go to the main screen
                        Toast.makeText(context, "Data exits",Toast.LENGTH_SHORT).show();
                        addStudentUser(context,user,classId,dataBase);
                    }
                    else {
                        Toast.makeText(context, "Error!!! Id is not existed.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(context, "Error!!! Invalid Class Id Code.",Toast.LENGTH_SHORT).show();
        }


    }


    public static void checkStudentId(String id, DatabaseReference dataBase, final Context context) {

        boolean formatChecker = Pattern.matches(Utils.userNameRegEx,id);
        if (formatChecker) {
            final String studentId = id;
            //check if classId exists in the database
            dataBase.child(Utils.USERS_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(studentId).exists()) {
                        //Go to the main screen
                        Toast.makeText(context, "Data exits",Toast.LENGTH_SHORT).show();
                        //Parent signUp sucessfully
                        User student = dataSnapshot.child(studentId).getValue(User.class);

                    }
                    else {
                        Toast.makeText(context, "Error!!! Student id is not existed.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(context, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(context, "Error!!! Invalid Student Id Code.",Toast.LENGTH_SHORT).show();
        }

    }

    private static void addStudentUser(final Context context, User user, String classId, DatabaseReference database){
        user.setClassId(classId);
        user.setfUserId(user.getLoginId());
        database.child(Utils.USERS_CHILD).child(user.getLoginId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Signed up successfully",Toast.LENGTH_SHORT).show();
            }
        });
        //add student id in class
        database.child(Utils.CLASSES_CHILD).child(classId).child(Utils.STUDENTS_CHILD).child(user.getfUserId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Student successfully",Toast.LENGTH_SHORT).show();
            }
        });

        String email = user.getLoginId().concat("@myStudent.com");
        String password = user.getPass();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //User registered successfully
                            Toast.makeText(context, "Signed up successfully",Toast.LENGTH_SHORT).show();
                        }else{
                            //need to go back to the signup screen
                            Log.i("Response","Failed to create user:"+task.getException().getMessage());
                        }
                    }
                });

    }


}

