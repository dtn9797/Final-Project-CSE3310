package com.example.duynguyen.sample.BehaviorEvaluationComp;

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
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationFragment extends Fragment {

    private String mClassId ="";
    private DatabaseReference mClassRef;

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
        getStudentsData();


        return view;

    }

    private void setUpView() {
        //RV
        EvaluationAdapter evaluationAdapter = new EvaluationAdapter(getContext(),mStudents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        evalRv.setAdapter(evaluationAdapter);
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
        com.example.duynguyen.sample.model.Student student0 = new Student("duy","nguyen","","","");
        com.example.duynguyen.sample.model.Student student1 = new Student("duy","nguyen","","","");
        mStudents.add(student0);
        mStudents.add(student1);
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
}
