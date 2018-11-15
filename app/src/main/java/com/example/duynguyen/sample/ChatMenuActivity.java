package com.example.duynguyen.sample;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.duynguyen.sample.Model.FriendlyMessage;
import com.example.duynguyen.sample.Model.MessageChannel;
import com.example.duynguyen.sample.utils.ChatChannelAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatMenuActivity extends AppCompatActivity implements ChatChannelAdapter.ItemListener {

    @BindView(R.id.chat_channel_rv)
    RecyclerView chatChannelRv;

    private DatabaseReference mDatabase;
    private NotificationCompat.Builder mBuilder;
    public String CHANNEL_ID = "12345";


    private String mUserType= "teacher";
    private ArrayList<MessageChannel> mMesChannels = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);
        ButterKnife.bind(this);

        inniMessChan();
        //Initialize database
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        //Initialize notification
//        iniNotification();
//        createNotificationChannel();

//        get ready for chatChannelRv
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ChatChannelAdapter chatChannelAdapter = new ChatChannelAdapter(this,mMesChannels,this);
        chatChannelRv.setAdapter(chatChannelAdapter);
        chatChannelRv.setLayoutManager(linearLayoutManager);


//        Button annChatBtn = findViewById(R.id.announcement_chat_btn);
//        annChatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //need to change this later
//                FirebaseMessaging.getInstance().subscribeToTopic("weather");
//                Intent intent = new Intent(getBaseContext(),ChatActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button notiBtn = findViewById(R.id.notification_btn);
//        notiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                notificationManager.notify(1111, mBuilder.build());
//
//            }
//        });
//


    }

    private void inniMessChan() {
        FriendlyMessage friendlyMessage = new FriendlyMessage("hi","Duy","teacher");
        List<FriendlyMessage> messages = new ArrayList<FriendlyMessage>();
        messages.add(friendlyMessage);
        MessageChannel messageChannel0 = new MessageChannel("announcement",messages);
        mMesChannels.add(messageChannel0);

        MessageChannel messageChannel1 = new MessageChannel("private",messages);
        mMesChannels.add(messageChannel1);
    }


    private void iniNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_arrow_back_black_24dp)
                .setContentTitle("Title")
                .setContentText("Content")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test channel";
            String description = "this is for test purpose";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onChannelClick(int position) {
        Toast.makeText(getBaseContext(),"hello",Toast.LENGTH_LONG).show();
    }
}
