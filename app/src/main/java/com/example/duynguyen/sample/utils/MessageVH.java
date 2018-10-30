package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.duynguyen.sample.R;

public class MessageVH extends RecyclerView.ViewHolder {
    TextView messageTv;

    public MessageVH(@NonNull View itemView) {
        super(itemView);
        messageTv = itemView.findViewById(R.id.message_tv);
    }

    public void setMessageTv(String message) {
        this.messageTv.setText(message);
    }
}
