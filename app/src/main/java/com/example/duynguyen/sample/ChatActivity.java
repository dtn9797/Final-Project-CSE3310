package com.example.duynguyen.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.sample.Model.FriendlyMessage;
import com.example.duynguyen.sample.utils.ChatAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {
    public String MESSAGES_CHILD = "messages";
    public String ANNOUNCEMENT_CHANNEL_CHILD = "announcement";
    public String PRIVATE_CHANNEL_CHILD = "privates";

    public String mUserType = "teacher";
    public String mParentId ;
    public String mTeacherId;

    private DatabaseReference mDatabase;
    private ChatAdapter mFirebaseAdapter;

    @BindView(R.id.profile_Iv)
    ImageView profileIv;
    @BindView(R.id.profile_Tv)
    TextView profileTv;
    @BindView(R.id.chat_rv)
    RecyclerView chatRv;
    @BindView(R.id.messageEditText)
    EditText messageEt;
    @BindView(R.id.sendButton)
    Button sendBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setUpView();
    }

    private void setUpView() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        SnapshotParser<FriendlyMessage> parser = new SnapshotParser<FriendlyMessage>(){
            @NonNull
            @Override
            public FriendlyMessage parseSnapshot(@NonNull DataSnapshot snapshot) {
                FriendlyMessage friendlyMessage = snapshot.getValue(FriendlyMessage.class);
                if (friendlyMessage != null) {
                    friendlyMessage.setId(snapshot.getKey());
                }
                return friendlyMessage;
            }
        };

        DatabaseReference messagesRef = mDatabase.child(MESSAGES_CHILD).child(ANNOUNCEMENT_CHANNEL_CHILD);
        FirebaseRecyclerOptions<FriendlyMessage> options =
                new FirebaseRecyclerOptions.Builder<FriendlyMessage>()
                        .setQuery(messagesRef, parser)
                        .build();
        mFirebaseAdapter = new ChatAdapter(options, mUserType);
        chatRv.setAdapter(mFirebaseAdapter);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRv.setLayoutManager(linearLayoutManager);


        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    chatRv.scrollToPosition(positionStart);
                }
            }
        });

//        Need to set up profile tv and iv
        if (mUserType.equals("parent")){
            sendBtn.setEnabled(false);
        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendlyMessage friendlyMessage = new
                        FriendlyMessage(messageEt.getText().toString(),
                        "userName",
                        mUserType,
                        null,
                        null /* no image */);
                mDatabase.child(MESSAGES_CHILD).child(ANNOUNCEMENT_CHANNEL_CHILD)
                        .push().setValue(friendlyMessage);
                messageEt.setText("");
            }
        });
    }

    @Override
    public void onPause() {
        mFirebaseAdapter.stopListening();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseAdapter.startListening();
    }
}
