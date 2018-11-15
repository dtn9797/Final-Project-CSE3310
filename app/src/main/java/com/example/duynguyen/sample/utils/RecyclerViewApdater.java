package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewApdater extends RecyclerView.Adapter<RecyclerViewApdater.RecyclerVh> {
    List<User> mData = new ArrayList<>();

    public RecyclerViewApdater(List<User> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new RecyclerVh(inflater.inflate(R.layout.view_holder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVh recyclerVh, int i) {
        recyclerVh.setData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class RecyclerVh extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;

        public RecyclerVh(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            subTitle = itemView.findViewById(R.id.subTitle_tv);


        }

        public void setData(User data){
            title.setText(data.getName());
            subTitle.setText(data.getId());
        }

    }
}
