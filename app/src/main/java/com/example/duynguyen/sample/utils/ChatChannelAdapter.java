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

public class ChatChannelAdapter extends RecyclerView.Adapter<ChatChannelAdapter.ChatChannelVH> {

    Context mContext;
    ArrayList<MessageChannel> mData = new ArrayList<>();
    protected static ItemListener mItemListener;


    public ChatChannelAdapter(Context mContext, ArrayList<MessageChannel> mData, ItemListener itemListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemListener = itemListener;
    }

    public interface ItemListener {
        void onChannelClick(int position);
    }

    @NonNull
    @Override
    public ChatChannelVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ChatChannelVH(inflater.inflate(R.layout.chat_channel_vh, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ChatChannelVH chatChannelVH, int i) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ChatChannelVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        int posVh = 0;

        public ChatChannelVH(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            ChatChannelAdapter.mItemListener.onChannelClick(posVh);
        }

    }
}
