package com.example.duynguyen.sample.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.duynguyen.sample.model.ClassRoom;
import com.example.duynguyen.sample.model.Parent;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.Teacher;
import com.example.duynguyen.sample.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FirebaseUtils {

    private static void addStudentUserToDatabase(final Context context, User user, String classId){
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Student student =new Student(user);
        student.setClassId(classId);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        student.setfUserId(auth.getCurrentUser().getUid());
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = reference.child(Utils.USERS_CHILD);
        DatabaseReference classRef = reference.child(Utils.CLASSES_CHILD);
        Parent parent = new Parent(user);
        parent.setStudentId(studentId);
        parent.setClassId(classId);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        parent.setfUserId(auth.getCurrentUser().getUid());
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
        //add messag private channel
        DatabaseReference messagesRef = reference.child(Utils.MESSAGES_CHILD);
        messagesRef.child(classId).child(parent.getfUserId()).setValue("");

    }

    private static void addTeacherToDatabase (final Context context, User user, String classId){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Teacher teacher = new Teacher(user);
        teacher.setClassId(classId);
        DatabaseReference userRef = reference.child(Utils.USERS_CHILD);
        DatabaseReference classRef = reference.child(Utils.CLASSES_CHILD);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        teacher.setfUserId(Objects.requireNonNull(auth.getCurrentUser()).getUid());
        //add parent id in users
        userRef.child(teacher.getfUserId()).setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Teacher successfully in users",Toast.LENGTH_SHORT).show();
            }
        });

        //add parent id in class
        classRef.child(classId).child(Utils.TEACHER_CHILD).child(teacher.getfUserId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Added Teacher successfully in their classes",Toast.LENGTH_SHORT).show();
            }
        });
        //add messag announcement channel
        DatabaseReference messagesRef = reference.child(Utils.MESSAGES_CHILD);
        messagesRef.setValue(classId);
        messagesRef.child(classId).child(Utils.ANNOUNCEMENT_CHILD).setValue("");
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
                            }else {
                                addTeacherToDatabase(context,user,classId);
                            }
                        }else{
                            //need to go back to the signup screen
                            Log.i("Response","Failed to create user:"+task.getException().getMessage());
                        }
                    }
                });
    }

    public static void addClass (ClassRoom classRoom) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        DatabaseReference classRef = reference.child(Utils.CLASSES_CHILD);
        classRef.child(classRoom.getClassId()).setValue(classRoom);

    }
}

