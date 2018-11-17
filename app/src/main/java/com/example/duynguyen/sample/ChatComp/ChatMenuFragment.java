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
    private NotificationCompat.Builder mBuilder;
    public String CHANNEL_ID = "12345";


    private String mUserType ;
    private String mClassId ;
    private ArrayList<com.example.duynguyen.sample.Model.MessageChannel> mMesChannelKeys = new ArrayList<>();
    private ChatChannelAdapter mChatChannelAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_menu,container,false);
        ButterKnife.bind(this,view);

        mMessRef = FirebaseDatabase.getInstance().getReference().child("messages");
        User currentUser = getCurrentUserInfo();
        mClassId = currentUser.getClassId();
        mUserType = currentUser.getUserType();
        inniMessChan();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mChatChannelAdapter = new ChatChannelAdapter(currentUser, mMesChannelKeys, this);
        chatChannelRv.setAdapter(mChatChannelAdapter);
        chatChannelRv.setLayoutManager(linearLayoutManager);

        return view;
    }

    private void inniMessChan() {

        mMessRef.child(mClassId).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mMesChannelKeys = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            com.example.duynguyen.sample.Model.MessageChannel messageChannel  = postSnapshot.getValue(com.example.duynguyen.sample.Model.MessageChannel.class);
                            mMesChannelKeys.add(messageChannel);
                        }
                        mChatChannelAdapter.setmData(mMesChannelKeys);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }


//    private void iniNotification() {
//        // Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, ChatActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_arrow_back_white_24dp)
//                .setContentTitle("Title")
//                .setContentText("Content")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//    }
//
//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "test channel";
//            String description = "this is for test purpose";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    private User getCurrentUserInfo() {
        final SharedPreferences mPrefs = Objects.requireNonNull(getActivity()).getPreferences(getActivity().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Utils.CURRENT_USER_KEY, "");
        return gson.fromJson(json, User.class);
    }

    @Override
    public void onChannelClick(String key) {
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        intent.putExtra(ChatActivity.CLASS_ID_EXTRA,mClassId);
        intent.putExtra(ChatActivity.KEY_ID_EXTRA,key);
        intent.putExtra(ChatActivity.USER_TYPE_EXTRA,mUserType);
        startActivity(intent);
    }
}
