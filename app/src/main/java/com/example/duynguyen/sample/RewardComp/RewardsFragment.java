package com.example.duynguyen.sample.RewardComp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.CloudImage;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.FirebaseUtils;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class RewardsFragment extends Fragment {

    @BindView(R.id.reward_rv)
    RecyclerView rewardRv;
    @BindView(R.id.add_reward_pts_rv)
    RecyclerView addPointsRv;
    @BindView(R.id.send_pts_btn)
    Button sendPtsBtn;

    private User mCurrentUser;
    private RewardItemsAdapter mRIAdapter;
    private DatabaseReference mRef;
    private List<Student> mStudents= new ArrayList<>();
    private AddRewardPtsAdapter mARPAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        ButterKnife.bind(this,view);

        //set Fb
        mRef = FirebaseDatabase.getInstance().getReference();
        //setup Rv for student type
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        mRIAdapter = new RewardItemsAdapter(getContext());
        rewardRv.setAdapter(mRIAdapter);
        rewardRv.setLayoutManager(gridLayoutManager);
        //setupRv for teacher type
        mARPAdapter = new AddRewardPtsAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        addPointsRv.setAdapter(mARPAdapter);
        addPointsRv.setLayoutManager(linearLayoutManager);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setUpview();
    }

    private void setUpview() {
        mCurrentUser = getCurrentUserInfo();
        String userType = mCurrentUser.getUserType();
        if (userType.equals(Utils.STUDENT)) {
            sendPtsBtn.setVisibility(View.GONE);

            mRef.child(Utils.USERS_CHILD).child(mCurrentUser.getfUserId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Student student = dataSnapshot.getValue(Student.class);
                    assert student != null;
                    List<CloudImage> rewardItems = student.getRewardPics();
                    mRIAdapter.setmRewardItems(rewardItems,student);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        //if current username is teacher
        else {
            getStudentsData();
            sendPtsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateStudentsPts();
                    Toast.makeText(getContext(),"Sent Reward Points",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updateStudentsPts() {
        for (int i = 0; i < mStudents.size(); i++) {
            View view = addPointsRv.getChildAt(i);
            Spinner spinner = view.findViewById(R.id.pts_spinner);
            int currentPts = Integer.parseInt(spinner.getSelectedItem().toString());
            if(currentPts != 0){
                Student currentStudent = mStudents.get(i);
                int totalPts = currentStudent.getRewardPts()+ currentPts;
                currentStudent.setRewardPts(totalPts);

                FirebaseUtils.updateStudentInfo(currentStudent);
            }
        }
    }


    private User getCurrentUserInfo() {
        final SharedPreferences mPrefs = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Utils.CURRENT_USER_KEY, "");
        return gson.fromJson(json, User.class);
    }

    private void getStudentsData() {
        //get from current user
        mCurrentUser = getCurrentUserInfo();
        String classId = mCurrentUser.getClassId();
        DatabaseReference studentsRef = mRef.child(Utils.CLASSES_CHILD).child(classId).child(Utils.STUDENTS_CHILD);
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> studentIds = new ArrayList<>();
                mStudents = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String studentId = snapshot.getValue(String.class);
                    studentIds.add(studentId);
                }
                for (String studentId: studentIds){
                    DatabaseReference studentRef = mRef.child(Utils.USERS_CHILD).child(studentId);
                    studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Student student = dataSnapshot.getValue(Student.class);
                            mStudents.add(student);
                            mARPAdapter.setmStudents(mStudents);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
