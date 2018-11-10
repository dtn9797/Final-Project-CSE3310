package com.example.duynguyen.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duynguyen.sample.utils.CodeValidation;
import com.example.duynguyen.sample.utils.Utils;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String mUserType;
    public static String USER_EXTRA = "userEx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_scanner);


        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            mUserType = intent.getParcelableExtra(USER_EXTRA);
        }


        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }


    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result result) {
        //Show result for debugging
        Toast.makeText(this, "Contents = " + result.getText() +
                ", Format = " + result.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        //temporary use below portion (47->51). Description: validate class/student id.
        if (mUserType.equals(Utils.PARENT)) {
            CodeValidation.checkStudentId(result.getText(), CodeInputActivity.mDatabase, getBaseContext());
        } else if (mUserType.equals(Utils.STUDENT)) {
//            CodeValidation.checkClassId(result.getText(), CodeInputActivity.mDatabase, getBaseContext());
        }

        //call scanner activity again
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScannerActivity.this);
            }
        }, 2000);
    }

//    public void createUser(String email, String password){
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            //go to scanner activity
//                            String getFullName = fullName.getText().toString();
//                            String getEmailId = userId.getText().toString();
//                            String getMobileNumber = mobileNumber.getText().toString();
//                            Teacher teacher = new Teacher(getFullName,getEmailId,classId,getMobileNumber);
//                            DatabaseReference teacherUserRef = mRef.child("users").child(classId+"/"+userType).push();
//                            String key = teacherUserRef.getKey();
//                            teacher.setId(key);
//                            teacherUserRef.setValue(teacher);
//
//                            Toast.makeText(getApplicationContext(), "Do SignUp.", Toast.LENGTH_SHORT)
//                                    .show();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            new CustomToast().Show_Toast(getApplicationContext(), view,
//                                    "Failed to register user(Internetion lost / duplicated user).");
////                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//    }
}
