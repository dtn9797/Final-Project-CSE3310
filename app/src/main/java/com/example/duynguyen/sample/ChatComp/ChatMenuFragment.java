package com.example.duynguyen.sample.ChatComp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.ChatChannelAdapter;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatMenuFragment extends Fragment implements ChatChannelAdapter.ItemListener {

    @BindView(R.id.chat_channel_rv)
    RecyclerView chatChannelRv;

    private DatabaseReference mMessRef;
    private DatabaseReference mRef;
    private NotificationCompat.Builder mBuilder;
    public String CHANNEL_ID = "12345";


    private String mUserType ;
    private String mClassId ;
    private ArrayList<com.example.duynguyen.sample.model.MessageChannel> mMesChannels = new ArrayList<>();
    private ChatChannelAdapter mChatChannelAdapter;
    private User mCurrentUser ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_menu,container,false);
        ButterKnife.bind(this,view);

        mMessRef = FirebaseDatabase.getInstance().getReference().child("messages");
        mRef = FirebaseDatabase.getInstance().getReference();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mChatChannelAdapter = new ChatChannelAdapter( this);
        chatChannelRv.setAdapter(mChatChannelAdapter);
        chatChannelRv.setLayoutManager(linearLayoutManager);

        getCurrentUserInfo();

        return view;
    }

    private void inniMessChan() {

        mMessRef.child(mClassId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mMesChannels = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            com.example.duynguyen.sample.model.MessageChannel messageChannel  = postSnapshot.getValue(com.example.duynguyen.sample.model.MessageChannel.class);
                            mMesChannels.add(messageChannel);
                        }
                        mChatChannelAdapter.setmData(mCurrentUser,mMesChannels);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }




    private void getCurrentUserInfo() {
        String currentUserId = FirebaseAuth.getInstance().getUid();
        mRef.child(Utils.USERS_CHILD).child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCurrentUser = dataSnapshot.getValue(User.class);
                mClassId = mCurrentUser.getClassId();
                mUserType = mCurrentUser.getUserType();
                inniMessChan();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onChannelClick(String key, int profileImageRes, String receiverName, String channelInfo) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        intent.putExtra(ChatActivity.CLASS_ID_EXTRA,mClassId);
        intent.putExtra(ChatActivity.KEY_ID_EXTRA,key);
        intent.putExtra(ChatActivity.USER_TYPE_EXTRA,mUserType);
        intent.putExtra(ChatActivity.PROFILE_PIC_EXTRA, profileImageRes);
        intent.putExtra(ChatActivity.RECEVIER_NAME_EXRTA,receiverName);
        intent.putExtra(ChatActivity.CHANNEL_INFO_EXTRA,channelInfo);
        startActivity(intent);
    }
}
