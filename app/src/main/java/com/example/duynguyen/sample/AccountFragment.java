package com.example.duynguyen.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.sample.model.ClassRoom;
import com.example.duynguyen.sample.model.CloudImage;
import com.example.duynguyen.sample.model.Evaluation;
import com.example.duynguyen.sample.model.Parent;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.AccountEvalAdapter;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    @BindView(R.id.student_iv)
    ImageView studentIv;
    @BindView(R.id.studentName_tv)
    TextView nameTv;
    @BindView(R.id.points_tv)
    TextView ptsTv;
    @BindView(R.id.eval_rv)
    RecyclerView evalRv;

    private Student mStudent;
    private DatabaseReference mRef;
    private AccountEvalAdapter mAEAdapter;
    private User mUser;
    private String mCurrentUserId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account, container, false);

        ButterKnife.bind(this, view);

        //setup RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAEAdapter = new AccountEvalAdapter();
        evalRv.setLayoutManager(linearLayoutManager);
        evalRv.setAdapter(mAEAdapter);
        //setup firebase
        mRef = FirebaseDatabase.getInstance().getReference();
        mCurrentUserId = FirebaseAuth.getInstance().getUid();
        getCurrentUser();
        return view;
    }

    private void checkUserType() {
        User currentUser = getCurrentUserInfo();
        if (currentUser.getUserType().equals(Utils.PARENT)) {
            mRef.child(Utils.USERS_CHILD).child(currentUser.getfUserId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Parent parent = dataSnapshot.getValue(Parent.class);
                    String studentId = parent.getStudentId();

                    mRef.child(Utils.USERS_CHILD).child(studentId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mStudent = dataSnapshot.getValue(Student.class);
                            setUpView();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (currentUser.getUserType().equals(Utils.STUDENT)) {
            mRef.child(Utils.USERS_CHILD).child(currentUser.getfUserId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mStudent = dataSnapshot.getValue(Student.class);
                    setUpView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void setUpView() {
        String studentName = mStudent.getFirstName() + " " + mStudent.getLastName();
        nameTv.setText(studentName);
        ptsTv.setText("1000 points");
        List<CloudImage> profilePics = mStudent.getProfilePics();
        //set Default profile Image
        for (CloudImage defaultProfilePic:profilePics){
            if (defaultProfilePic.getEnable()){
                Picasso.get().load(defaultProfilePic.getUrl()).into(studentIv);
            }
        }

        //populate Iv later
        HashMap<String, Evaluation> evaluationHashMap = mStudent.getEvaluations();
        if (evaluationHashMap != null) {
            List<Evaluation> evaluations = new ArrayList<>(evaluationHashMap.values());
            //set up recycler view
            mAEAdapter.setEvaluations(evaluations);
        }


    }

    private User getCurrentUserInfo() {
        final SharedPreferences mPrefs = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Utils.CURRENT_USER_KEY, "");
        return gson.fromJson(json, User.class);
    }

    public void getCurrentUser () {
        mRef.child(Utils.USERS_CHILD).child(mCurrentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
                checkUserType();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
