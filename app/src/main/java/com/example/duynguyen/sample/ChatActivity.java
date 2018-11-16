package com.example.duynguyen.sample;

import android.content.Intent;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatActivity extends AppCompatActivity {
    public String MESSAGES_CHILD = "messages";
    public String ANNOUNCEMENT_CHANNEL_CHILD = "announcement";
    public String PRIVATE_CHANNEL_CHILD = "privates";

    public static String CLASS_ID_EXTRA = "classid";
    public static String KEY_ID_EXTRA = "keyid";
    public static String USER_TYPE_EXTRA = "userid";

    //these below variables are for setting private/ announcement chat room w/ teacher & user id
    public String mUserType = "teacher";
    public boolean mAnnouncChannel = false;
    public String mClassId ;
    public String mParentId = "parentid2";
    //if user is a parent, get teacher info first before go to chat activity
    public String mTeacherId = "teacherid0";

    private DatabaseReference mRef, mNotificationRef;
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

        Intent intent = getIntent();
        mClassId = intent.getStringExtra(CLASS_ID_EXTRA);
        mUserType = intent.getStringExtra(USER_TYPE_EXTRA);
        String key = intent.getStringExtra(KEY_ID_EXTRA);
        if (key.equals(ANNOUNCEMENT_CHANNEL_CHILD)) mAnnouncChannel = true;

        setUpView();
    }

    private void setUpView() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mNotificationRef = mRef.child("notifications");

        final String messPath = ((mAnnouncChannel) ? MESSAGES_CHILD + "/" + mClassId + "/" + ANNOUNCEMENT_CHANNEL_CHILD
                : MESSAGES_CHILD + "/" + mClassId + "/" + (mTeacherId + "+" + mParentId));

        SnapshotParser<FriendlyMessage> parser = new SnapshotParser<FriendlyMessage>() {
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

        DatabaseReference messagesRef = mRef.child(messPath);
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
        if (mUserType.equals("parent") && mAnnouncChannel) {
            sendBtn.setEnabled(false);
        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendlyMessage friendlyMessage = new
                        FriendlyMessage(messageEt.getText().toString(),
                        "userName",
                        mUserType);
                mRef.child(messPath)
                        .push().setValue(friendlyMessage);

                //need to change it later
                HashMap<String, String> notMap = new HashMap<>();
                notMap.put("from", mTeacherId);
                notMap.put("type", "announcment");
                mNotificationRef.child(mClassId).push().setValue(notMap);

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
