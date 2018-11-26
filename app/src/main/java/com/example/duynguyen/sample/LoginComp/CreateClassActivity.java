package com.example.duynguyen.sample.LoginComp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.ClassRoom;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.CodeGenerator;
import com.example.duynguyen.sample.utils.CustomToast;
import com.example.duynguyen.sample.utils.FirebaseUtils;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateClassActivity extends AppCompatActivity {
    public static final String USER_EXTRA ="user" ;
    private static final String TAG = CreateClassActivity.class.getSimpleName();

    @BindView(R.id.className_et)
    public EditText classNameEt;
    @BindView(R.id.creat_class_btn)
    public Button createClassBtn;

    private DatabaseReference mRef;
    private String mClassId;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        ButterKnife.bind(this);

        //check if class id is existed in the database
        mRef = FirebaseDatabase.getInstance().getReference();
        populateClassId();

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            } else {
                mUser = intent.getParcelableExtra(USER_EXTRA);
            }
        }
        createClassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String className = classNameEt.getText().toString();

                if (className.equals("") || className.length() == 0)

                    new CustomToast().Show_Toast(getApplicationContext(), view,
                            "All fields are required.");
                else if (mClassId.equals("") || mClassId.length() == 0){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    onClick(view);
                }
                else {
                    ClassRoom classRoom = new ClassRoom(className,mClassId);
                    FirebaseUtils.addClass(classRoom);
                    FirebaseUtils.signIn(mUser.getLoginId(),mUser.getPass(),mClassId,"",classRoom,mUser,getBaseContext());
                }

            }
        });
    }

    private void populateClassId(){
        mRef.child(Utils.CLASSES_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String classId = CodeGenerator.generateClassId();
                if (!dataSnapshot.child(classId).exists()) {
                    mClassId = classId;
                }
                else {
                    populateClassId();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG,databaseError.getMessage());
            }
        });
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
    }

}
