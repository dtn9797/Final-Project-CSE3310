package com.example.duynguyen.sample.BehaviorEvaluationComp;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class EvaluationFragment extends Fragment {

    private User mCurrentUser ;
    private DatabaseReference mRef;
    private EvaluationAdapter mEvalAdap;

    private List<com.example.duynguyen.sample.model.Student> mStudents = new ArrayList<>();

    @BindView(R.id.evaluation_rv)
    RecyclerView evalRv;
    @BindView(R.id.eval_submit_btn)
    Button evalSubmitBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_evaluation, container, false);
        ButterKnife.bind(this,view);

        setUpView();
        mRef = FirebaseDatabase.getInstance().getReference();
        getStudentsData();


        return view;

    }

    private void setUpView() {
        //RV
        mEvalAdap = new EvaluationAdapter(getContext(),mStudents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        evalRv.setAdapter(mEvalAdap);
        evalRv.setLayoutManager(linearLayoutManager);
        //submit btn
        evalSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveStudentsData();
            }
        });
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
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String studentId = snapshot.getValue(String.class);
                    studentIds.add(studentId);
                }
                for (String studentId: studentIds){
                    DatabaseReference studentRef = mRef.child(Utils.USERS_CHILD).child(studentId);
                    studentRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Student student = dataSnapshot.getValue(Student.class);
                            mStudents.add(student);
                            mEvalAdap.setmData(mStudents);
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

    private void retriveStudentsData(){
        for (int i = 0; i < mStudents.size(); i++) {
            View view = evalRv.getChildAt(i);
            EditText commentEt = (EditText) view.findViewById(R.id.eval_et);
            String comment = commentEt.getText().toString();
            Spinner evalSpinner = view.findViewById(R.id.eval_spinner);
            String behavior = evalSpinner.getSelectedItem().toString();
            String text = "";

        }
    }
    private User getCurrentUserInfo() {
        final SharedPreferences mPrefs = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Utils.CURRENT_USER_KEY, "");
        return gson.fromJson(json, User.class);
    }
}
