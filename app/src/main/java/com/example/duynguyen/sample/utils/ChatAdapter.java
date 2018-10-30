package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.duynguyen.sample.Model.FriendlyMessage;
import com.example.duynguyen.sample.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChatAdapter extends FirebaseRecyclerAdapter<FriendlyMessage,MessageVH> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdapter(@NonNull FirebaseRecyclerOptions<FriendlyMessage> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageVH holder, int position, @NonNull FriendlyMessage model) {
        holder.setMessageTv(model.getText());
    }

    @NonNull
    @Override
    public MessageVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new MessageVH(inflater.inflate(R.layout.outcoming_message_vh,viewGroup,false));
    }
}
