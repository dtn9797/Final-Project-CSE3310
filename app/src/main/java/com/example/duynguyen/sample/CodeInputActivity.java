package com.example.duynguyen.sample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.duynguyen.sample.utils.CodeValidation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CodeInputActivity extends AppCompatActivity implements View.OnClickListener {



    private static final int ZXING_CAMERA_PERMISSION = 1;
    @BindView(R.id.enter_code_btn)
    Button enterCodeBtn;
    @BindView(R.id.enter_code_et)
    EditText enterCodeEt;
    @BindView(R.id.scan_code_btn)
    Button scanCodeBtn;

    //temporary place variables as static
    private FirebaseAuth mAuth;
    public static DatabaseReference mDatabase;
    public static String userType = "parent";
    public static String PARENT_USER = "parent";
    public static String STUDENT_USER = "student";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_input);
        ButterKnife.bind(this);

        setUpView();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void setUpView() {

        enterCodeBtn.setOnClickListener(this);
        scanCodeBtn.setOnClickListener(this);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_code_btn:
                if (userType.equals(PARENT_USER)) {
                    CodeValidation.checkStudentId(String.valueOf(enterCodeEt.getText()), mDatabase, getBaseContext());
                } else if (userType.equals(STUDENT_USER)) {
                    CodeValidation.checkClassId(String.valueOf(enterCodeEt.getText()), mDatabase, getBaseContext());
                }
                break;
            case R.id.scan_code_btn:
                launchActivity(ScannerActivity.class);
                break;
        }
    }
}