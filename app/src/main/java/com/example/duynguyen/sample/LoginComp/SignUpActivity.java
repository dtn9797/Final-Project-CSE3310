package com.example.duynguyen.sample.LoginComp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.CustomToast;
import com.example.duynguyen.sample.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity  extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = SignUpActivity.class.getSimpleName();

    private static View view;
    private static EditText firstNameEt, lastNameEt, userIdEt, mobileNumberEt,
            passwordEt, confirmPasswordEt;
    private static TextView login;
    private static Button nextBtn;
    private RadioGroup radUserTypeRG;
    private RadioButton studentRBtn,parentRBtn,teacherRBtn,rBtn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.activity_signup,
                (ViewGroup) findViewById(R.id.signUp_ll));

        initViews();
        setListeners();


    }

    // Set Listeners
    private void setListeners() {
        nextBtn.setOnClickListener(this);
        login.setOnClickListener(this);
        parentRBtn.setOnClickListener(this);
        teacherRBtn.setOnClickListener(this);
        studentRBtn.setOnClickListener(this);
    }

    private void initViews() {
        studentRBtn =findViewById(R.id.studentRadioBtn);
        parentRBtn=findViewById(R.id.parentRadioBtn);
        teacherRBtn =findViewById(R.id.teacherRadioBtn);
        radUserTypeRG = findViewById(R.id.user_type_RG);
        firstNameEt = (EditText) findViewById(R.id.first_name_edt);
        userIdEt = (EditText) findViewById(R.id.userEmailId);
        lastNameEt = (EditText) findViewById(R.id.last_name_dt);
        mobileNumberEt = (EditText) findViewById(R.id.mobileNumber);
        passwordEt = (EditText) findViewById(R.id.password);
        confirmPasswordEt = (EditText) findViewById(R.id.confirmPassword);
        nextBtn = (Button) findViewById(R.id.next_btn);
        login = (TextView) findViewById(R.id.already_user);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    private void checkValidation() {

        // Get all edittext texts
        String getUserType ="";
        String getFirstName = firstNameEt.getText().toString();
        String getLastName = lastNameEt.getText().toString();
        String getUserId = userIdEt.getText().toString();
        String getUserIdHintText = userIdEt.getHint().toString();
        String getMobileNumber = mobileNumberEt.getText().toString();
        String getPassword = passwordEt.getText().toString();
        String getConfirmPassword = confirmPasswordEt.getText().toString();

        // Pattern match for email id
        Pattern p0 = Pattern.compile(Utils.emailRegEx);
        Matcher m0 = p0.matcher(getUserId);

        // Pattern match for user id
        Pattern p1 = Pattern.compile(Utils.userNameRegEx);
        Matcher m1 = p1.matcher(getUserId);


        if (radUserTypeRG.getCheckedRadioButtonId() != -1){
            //get selected radio btn
            int selected = radUserTypeRG.getCheckedRadioButtonId();
            rBtn = (RadioButton) findViewById(selected);
            getUserType= rBtn.getText().toString();
        }
        // Check if all strings are null or not
        if (getUserType.equals("") || getUserType.length() == 0||
                getFirstName.equals("") || getFirstName.length() == 0
                || getUserId.equals("") || getUserId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLastName.equals("") || getLastName.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0){

            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "All fields are required.");
        }


        // Check if email id, user Id valid or not
        else if ((!m0.find()&& getUserIdHintText.equals("Email Id"))||
                !m1.find()&& getUserIdHintText.equals("User Id"))
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "Your Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getApplicationContext(), view,
                    "Both password doesn't match.");

            // Else do signup or do your stuff
        else {
            //Go to CodeInputActivity if user type is parent/student
            User user = new User(getUserType,getFirstName,getLastName,getMobileNumber,getUserId,getPassword);
            if (getUserType.equals(Utils.PARENT)||getUserType.equals(Utils.STUDENT)){
                Intent intent = new Intent(this,CodeInputActivity.class);
                intent.putExtra(CodeInputActivity.USER_EXTRA,user);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this,CreateClassActivity.class);
                intent.putExtra(CreateClassActivity.USER_EXTRA,user);
                startActivity(intent);
            }


        }

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        int id2 =  R.id.parentRadioBtn;
        switch (v.getId()) {

            case R.id.next_btn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:
                finish();

                break;
            case R.id.parentRadioBtn:
                userIdEt.setHint("Email Id");
                break;
            case R.id.studentRadioBtn:
                userIdEt.setHint("User Id");
                break;
            case R.id.teacherRadioBtn:
                userIdEt.setHint("Email Id");
                break;
        }

    }
}
