package com.example.duynguyen.sample.ChatComp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.sample.Model.FriendlyMessage;
import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.utils.ChatAdapter;
import com.example.duynguyen.sample.utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatActivity extends AppCompatActivity {

    public static String CLASS_ID_EXTRA = "classid";
    public static String KEY_ID_EXTRA = "keyid";
    public static String USER_TYPE_EXTRA = "userid";
    public static String RECEVIER_NAME_EXRTA = "name";
    public static String CHANNEL_INFO_EXTRA = "info";
    public static String PROFILE_PIC_EXTRA = "pro";


    //these below variables are for setting private/ announcement chat room w/ teacher & user id
    public String mUserType;
    public boolean mAnnouncChannel = false;
    public String mClassId;
    public String mParentId;
    public String mReceiverName;
    public int mProfilePicRes;
    public String mChanelInfo;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        //get values from intent and set if it is announcement or private type
        Intent intent = getIntent();
        mClassId = intent.getStringExtra(CLASS_ID_EXTRA);
        mUserType = intent.getStringExtra(USER_TYPE_EXTRA);
        String key = intent.getStringExtra(KEY_ID_EXTRA);
        mReceiverName = intent.getStringExtra(RECEVIER_NAME_EXRTA);
        mChanelInfo = intent.getStringExtra(CHANNEL_INFO_EXTRA);
        mProfilePicRes = intent.getIntExtra(PROFILE_PIC_EXTRA,-1);

        if (key.equals(Utils.ANNOUNCEMENT_CHILD)) mAnnouncChannel = true;
        else {
            mParentId = key;
        }

        setUpView();
    }

    private void setUpView() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mNotificationRef = mRef.child("notifications");

        //A path for storing messages
        final String messPath = ((mAnnouncChannel) ? Utils.MESSAGES_CHILD + "/" + mClassId + "/" + Utils.ANNOUNCEMENT_CHILD+"/"+Utils.CONVERSATION_CHILD
                : Utils.MESSAGES_CHILD + "/" + mClassId + "/" + mParentId+"/"+Utils.CONVERSATION_CHILD);

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
        if (mUserType.equals(Utils.PARENT) && mAnnouncChannel) {
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

//                //need to change it later
//                HashMap<String, String> notMap = new HashMap<>();
//                notMap.put("from", mTeacherId);
//                notMap.put("type", "announcment");
                mNotificationRef.child(mClassId).child(Utils.ANNOUNCEMENT_CHILD + mClassId).push().setValue("");

                messageEt.setText("");
            }
        });

        getSupportActionBar().hide();
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //populate toolbar view
        profileIv.setImageResource(mProfilePicRes);
        profileTv.setText(mReceiverName);
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
