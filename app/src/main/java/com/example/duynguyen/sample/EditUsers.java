package com.example.duynguyen.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.duynguyen.sample.model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUsers extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String type, fname, lname, password, email, phone, student, parent;
    private RadioGroup radGroup;
    private RadioButton rBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_users);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Radio button group to specify user type
        radGroup = (RadioGroup) findViewById(R.id.radioGroup);

        // Edit Text fields
        final EditText etFname = findViewById(R.id.et_fname);
        final EditText etLname = findViewById(R.id.et_lname);
        final EditText etPassword = findViewById(R.id.et_password);
        final EditText etEmail = findViewById(R.id.et_email);
        final EditText etPhone = findViewById(R.id.et_phone);
        final EditText etStudent = findViewById(R.id.et_student);
        final EditText etParent = findViewById(R.id.et_parent);

        // Add user button
        Button addBtn = (Button) findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radGroup.getCheckedRadioButtonId();
                rBtn = (RadioButton) findViewById(selected);
                type = rBtn.getText().toString();
                fname = etFname.getText().toString();
                lname = etLname.getText().toString();
                password = etPassword.getText().toString();
                email = etEmail.getText().toString();
                phone = etPhone.getText().toString();
                parent = etParent.getText().toString();
                student = etStudent.getText().toString();

                if(type.equals("Parent")){
                    student = etStudent.getText().toString();
                    DatabaseReference parentRef = mDatabase.child("Users/Parents/"+lname);
                    DatabaseReference pushedRef = parentRef.push();
                    String key = pushedRef.getKey();
                    Users user = new Users(type, key, fname,lname,password,email,phone,student,null);
                    pushedRef.setValue(user);
                }
                else if(type.equals("Student")){
                    parent = etParent.getText().toString();
                    DatabaseReference studentRef = mDatabase.child("Users/Students/"+lname);
                    DatabaseReference pushedRef = studentRef.push();
                    String key = pushedRef.getKey();
                    Users user = new Users(type, key, fname, lname, password, email, phone, null, parent);
                    pushedRef.setValue(user);
                }else if(type.equals("Teacher")){
                    DatabaseReference teacherRef = mDatabase.child("Users/Teacher/"+lname);
                    DatabaseReference pushedRef = teacherRef.push();
                    String key = pushedRef.getKey();
                    Users user = new Users(type, key, fname, lname, password, email, phone, null, null);
                    pushedRef.setValue(user);
                }

                
            }
        });
    }

}
