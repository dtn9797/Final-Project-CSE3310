package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.Evaluation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountEvalAdapter extends RecyclerView.Adapter<AccountEvalAdapter.AccountEvalVh> {
    List<Evaluation> mEvaluations= new ArrayList<>();

    public AccountEvalAdapter() {
    }

    @NonNull
    @Override
    public AccountEvalVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new AccountEvalVh(inflater.inflate(R.layout.student_eval_vh, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountEvalVh accountEvalVh, int i) {
        accountEvalVh.setData(mEvaluations.get(i));
    }

    @Override
    public int getItemCount() {
        return mEvaluations.size();
    }

    public void setEvaluations(List<Evaluation> mEvaluations) {
        this.mEvaluations = mEvaluations;
        notifyDataSetChanged();
    }

    public class AccountEvalVh extends RecyclerView.ViewHolder {
        @BindView(R.id.behavior_iv)
        ImageView behaviorIv;
        @BindView(R.id.behavior_tv)
        TextView behavTv;
        @BindView(R.id.comment_tv)
        TextView comTv;
        @BindView(R.id.date_tv)
        TextView dateTv;

        public AccountEvalVh(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        public void setData (Evaluation evaluation){
            String behavior = evaluation.getBehavior();
            behavTv.setText(behavior);
            String comment = "Comment: " + evaluation.getComment();
            comTv.setText(comment);
            dateTv.setText(evaluation.getDate());
            switch (behavior){
                case Utils.EXCELLENT_BEHAV:
                    behaviorIv.setImageResource(R.drawable.a);
                    break;

                case Utils.GOOD_BEHAV:
                    behaviorIv.setImageResource(R.drawable.b);
                    break;

                case Utils.FAIR_BEHAV:
                    behaviorIv.setImageResource(R.drawable.c);
                    break;

                case Utils.NI_BEHAV:
                    behaviorIv.setImageResource(R.drawable.d);
                    break;
            }
        }
    }
}
