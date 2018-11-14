package com.example.duynguyen.sample.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duynguyen.sample.Model.MessageChannel;
import com.example.duynguyen.sample.R;

import java.util.ArrayList;

public class ChatChannelAdapter extends RecyclerView.Adapter<ChatChannelAdapter.ChatProfileVH> {

    Context mContext;
    ArrayList<MessageChannel> mData = new ArrayList<>();


    public ChatChannelAdapter(Context mContext, ArrayList<MessageChannel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ChatProfileVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ChatChannelAdapter.ChatProfileVH(inflater.inflate(R.layout.chat_channel_vh, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ChatProfileVH chatProfileVH, int i) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ChatProfileVH extends RecyclerView.ViewHolder {
        public ChatProfileVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
