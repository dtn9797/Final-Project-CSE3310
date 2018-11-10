package com.example.duynguyen.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duynguyen.sample.utils.CodeValidation;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_scanner);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
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
        if (CodeInputActivity.userType.equals(CodeInputActivity.PARENT_USER)) {
            CodeValidation.checkStudentId(result.getText(), CodeInputActivity.mDatabase, getBaseContext());
        } else if (CodeInputActivity.userType.equals(CodeInputActivity.STUDENT_USER)) {
            CodeValidation.checkClassId(result.getText(), CodeInputActivity.mDatabase, getBaseContext());
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
