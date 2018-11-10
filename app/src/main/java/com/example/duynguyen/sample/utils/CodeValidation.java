package com.example.duynguyen.sample.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.duynguyen.sample.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CodeValidation {

    public static void checkClassId(String id, final DatabaseReference dataBase, final Context context) {
        //Check id format
        boolean formatChecker = Pattern.matches("mystudent[0-9]{5,5}$", id);
        if (formatChecker) {
            final String classId = id.substring(9, 14);
            //check if classId exists in the database
            dataBase.child("classes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(classId).exists()) {
                        //Go to the main screen
                        Toast.makeText(context, "Data exits",Toast.LENGTH_SHORT).show();
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


}

