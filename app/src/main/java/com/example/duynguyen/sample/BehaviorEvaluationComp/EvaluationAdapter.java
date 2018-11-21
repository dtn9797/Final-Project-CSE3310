package com.example.duynguyen.sample.BehaviorEvaluationComp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationVH> {
    List<com.example.duynguyen.sample.model.Student> mStudents = new ArrayList<>();
    Context mContext;

    public EvaluationAdapter(Context context, List<Student> mStudents) {
        mContext = context;
        this.mStudents = mStudents;
    }

    @NonNull
    @Override
    public EvaluationVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new EvaluationVH(inflater.inflate(R.layout.evaluation_vh, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationVH evaluationVH, int i) {
        evaluationVH.setData(mStudents.get(i));
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public void setmData(List<com.example.duynguyen.sample.model.Student> students ){
        mStudents = students;
        notifyDataSetChanged();
    }

    public class EvaluationVH extends RecyclerView.ViewHolder {
        @BindView(R.id.eval_spinner)
        Spinner evalSpin;
        @BindView(R.id.student_name_tv)
        TextView nameTv;
        public EvaluationVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Student student){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                    R.array.evaluations_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            evalSpin.setAdapter(adapter);

            String studentName = student.getFirstName()+" "+student.getLastName();
            nameTv.setText(studentName);

        }
    }
}
