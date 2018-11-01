package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duynguyen.sample.Model.FriendlyMessage;
import com.example.duynguyen.sample.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatAdapter extends FirebaseRecyclerAdapter<FriendlyMessage, ChatAdapter.MessageVH> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    String mUserType;

    public ChatAdapter(@NonNull FirebaseRecyclerOptions<FriendlyMessage> options, String userType) {
        super(options);
        mUserType = userType;
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageVH holder, int position, @NonNull FriendlyMessage model) {
        if (mUserType.equals(model.getUserType()) ) {
            holder.setOutcomingMessage(model.getText());
        }else {
            holder.setIncomingMessage(model.getText());
        }

    }

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MessageVH(inflater.inflate(R.layout.message_vh, viewGroup, false));
    }

    public class MessageVH extends RecyclerView.ViewHolder {
        @BindView(R.id.incoming_message_tv)
        TextView incomingMessageTv;
        @BindView(R.id.outcoming_message_tv)
        TextView outcomingMessageTv;
        @BindView(R.id.incoming_message_ll)
        LinearLayout incomingLl;
        @BindView(R.id.outcoming_message_ll)
        LinearLayout outcomingLl;

        public MessageVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setIncomingMessage(String message) {
            this.incomingMessageTv.setText(message);
            outcomingLl.setVisibility(View.GONE);
            incomingLl.setVisibility(View.VISIBLE);
        }

        public void setOutcomingMessage(String message) {
            outcomingMessageTv.setText(message);
            outcomingLl.setVisibility(View.VISIBLE);
            incomingLl.setVisibility(View.GONE);
        }
    }
}
