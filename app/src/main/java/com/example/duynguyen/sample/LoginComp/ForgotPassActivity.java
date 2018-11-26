package com.example.duynguyen.sample.LoginComp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.utils.CustomToast;
import com.example.duynguyen.sample.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ForgotPassActivity.class.getSimpleName();
    @BindView(R.id.backToLoginBtn)
    TextView backToLoginBtn;
    @BindView(R.id.forgot_button)
    TextView forgotBtn;
    @BindView(R.id.registered_emailid_et)
    EditText registedEmailEt;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        ButterKnife.bind(this);

        setListeners();
        mAuth = FirebaseAuth.getInstance();
    }

    // Set Listeners over buttons
    private void setListeners() {
        backToLoginBtn.setOnClickListener(this);
        forgotBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                finish();
                break;

            case R.id.forgot_button:

                // Call Submit button task
                submitButtonTask(v);
                break;

        }

    }

    private void submitButtonTask(final View view) {
        String getEmailId = registedEmailEt.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.emailRegEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getBaseContext(), view,
                    "Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else{
            mAuth.sendPasswordResetEmail(getEmailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Check your email to retrieve password.",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        new CustomToast().Show_Toast(getBaseContext(), view,
                                "Your Email Id is not Existed.");
                    }
                }
            });
            ;
        }
    }
}



