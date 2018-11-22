package com.example.duynguyen.sample.RewardComp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duynguyen.sample.BehaviorEvaluationComp.EvaluationAdapter;
import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.Student;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRewardPtsAdapter extends RecyclerView.Adapter<AddRewardPtsAdapter.AddRewardPtsVh> {
    private Context mContext;
    private List<Student> mStudents = new ArrayList<>();

    @NonNull
    @Override
    public AddRewardPtsVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new AddRewardPtsVh(inflater.inflate(R.layout.add_reward_pts_vh, viewGroup, false));

    }

    public AddRewardPtsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull AddRewardPtsVh addRewardPtsVh, int i) {
        addRewardPtsVh.setData(mStudents.get(i));
    }

    public void setmStudents(List<Student> mStudents) {
        this.mStudents = mStudents;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public class AddRewardPtsVh extends RecyclerView.ViewHolder {
        @BindView(R.id.student_na_tv)
        TextView studentNameTv;
        @BindView(R.id.pts_spinner)
        Spinner ptsSpinner;

        public AddRewardPtsVh(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Student student){
            String name = student.getFirstName() + " " +student.getLastName();
            studentNameTv.setText(name);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                    R.array.pts_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ptsSpinner.setAdapter(adapter);
        }
    }
}
