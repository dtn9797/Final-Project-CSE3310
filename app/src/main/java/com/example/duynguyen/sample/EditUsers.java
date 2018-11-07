package com.example.duynguyen.sample;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.duynguyen.sample.model.Users;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUsers extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView textViewFname;
    private TextView textViewLname;
    private TextView textViewEmail;
    private TextView textViewPassword;
    private TextView textViewPhone;
    private String type, fname, lname, password, email, phone;
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

        // Add user button
        Button addBtn = (Button) findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radGroup.getCheckedRadioButtonId();
                switch(selected){
                    case 0: type = "teacher"; break;
                    case 1: type = "parent"; break;
                    case 2: type = "student"; break;
                }
                fname = etFname.getText().toString();
                lname = etLname.getText().toString();
                password = etPassword.getText().toString();
                email = etEmail.getText().toString();
                phone = etPhone.getText().toString();
                Users user = new Users(type, fname,lname,password,email,phone);

                DatabaseReference userRef = mDatabase.child("Users");
                userRef.setValue(user);
            }
        });
    }

}
