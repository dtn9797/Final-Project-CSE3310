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

import com.example.duynguyen.sample.model.Evaluation;
import com.example.duynguyen.sample.model.Parent;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.AccountEvalAdapter;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        ;
        ButterKnife.bind(this, view);

        //setup RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mAEAdapter = new AccountEvalAdapter();
        evalRv.setLayoutManager(linearLayoutManager);
        evalRv.setAdapter(mAEAdapter);
        //setup firebase
        mRef = FirebaseDatabase.getInstance().getReference();
        loadStudentData();
        return view;
    }

    private void loadStudentData() {
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
        } else {
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
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/my-student-152dc.appspot.com/o/rewardItems%2F1.png?alt=media&token=9a55a700-147c-4f7f-a756-20a890e54037").into(studentIv);
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
}
