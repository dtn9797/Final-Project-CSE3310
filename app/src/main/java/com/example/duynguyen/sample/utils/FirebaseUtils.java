package com.example.duynguyen.sample.utils;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static void addStudentUserToDatabase(final Context context, User user, String classId){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Student student =new Student(user);
        student.setClassId(classId);
        student.setfUserId(user.getLoginId());
        //add student id in users
        database.child(Utils.USERS_CHILD).child(student.getfUserId()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Student successfully in users",Toast.LENGTH_SHORT).show();
            }
        });
        //add student id in class
        database.child(Utils.CLASSES_CHILD).child(classId).child(Utils.STUDENTS_CHILD).child(student.getfUserId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Student successfully in their classes",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private static void addParentUserToDatabase (final Context context, User user, String classId,String studentId){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child(Utils.USERS_CHILD);
        DatabaseReference classRef = FirebaseDatabase.getInstance().getReference().child(Utils.CLASSES_CHILD);
        Parent parent = new Parent(user);
        parent.setStudentId(studentId);
        parent.setClassId(classId);
        parent.setfUserId(userRef.push().getKey());
        //add parent id in users
        userRef.child(parent.getfUserId()).setValue(parent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Parent successfully in users",Toast.LENGTH_SHORT).show();
            }
        });
        //add parent id in class
        classRef.child(classId).child(Utils.PARENTS_CHILD).child(parent.getfUserId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Parent successfully in their classes",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void signIn (String email, String password, final String classId, final String studentId, final User user, final Context context){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //User registered successfully
                            Toast.makeText(context, "Signed up successfully",Toast.LENGTH_SHORT).show();
                            if(user.getUserType().equals(Utils.STUDENT)){
                                addStudentUserToDatabase(context, user,classId);
                            }else if (user.getUserType().equals(Utils.PARENT)){
                                addParentUserToDatabase(context, user,classId,studentId);
                            }
                        }else{
                            //need to go back to the signup screen
                            Log.i("Response","Failed to create user:"+task.getException().getMessage());
                        }
                    }
                });
    }
}

