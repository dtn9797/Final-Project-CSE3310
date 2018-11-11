package com.example.duynguyen.sample.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.duynguyen.sample.model.Parent;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CodeValidation {

    public static void checkClassId(String id,final User user, final Context context) {
        //Check id format
        boolean formatChecker = Pattern.matches("myStudent[0-9]{5,5}$", id);
        if (formatChecker) {
            final String classId = id;
            //check if classId exists in the database
            DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
            dataBase.child("classes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(classId).exists()) {
                        //Go to the main screen
                        Toast.makeText(context, "Data exits",Toast.LENGTH_SHORT).show();
                        String email = user.getLoginId().concat("@myStudent.com");
                        String password = user.getPass();
                        user.setClassId(classId);
                        FirebaseUtils.signIn(email,password,classId,"",user,context);
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


    public static void checkStudentId(String id,final User user, final Context context) {

        boolean formatChecker = Pattern.matches(Utils.studentKeyRegEx,id);
        if (formatChecker) {
            final String classId = id.substring(0,14);
            final String studentId = id.substring(14,id.length());
            //check if classId exists in the database
            DatabaseReference dataBase = FirebaseDatabase.getInstance().getReference();
            dataBase.child(Utils.CLASSES_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (!studentId.equals("")||studentId.length()!=0||dataSnapshot.child(classId+"/"+Utils.STUDENTS_CHILD
                            +"/"+studentId).exists()) {
                        //Go to the main screen
                        Toast.makeText(context, "Data exits",Toast.LENGTH_SHORT).show();
                        String email = user.getLoginId();
                        String pass = user.getPass();
                        FirebaseUtils.signIn(email,pass,classId,studentId,user,context);

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


}

