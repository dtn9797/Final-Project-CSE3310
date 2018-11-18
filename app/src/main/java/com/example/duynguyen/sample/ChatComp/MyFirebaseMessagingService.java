package com.example.duynguyen.sample.ChatComp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.duynguyen.sample.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationCompat.Builder mBuilder;
    public String CHANNEL_ID = "12345";

    private static final String TAG = "MyFMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

        createNotificationChannel();

        String title = remoteMessage.getData().get("title");
        String subtitle = remoteMessage.getData().get("subtitle");
        iniNotification( title, subtitle);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        int mNotificationID = (int)System.currentTimeMillis();
        notificationManager.notify(mNotificationID, mBuilder.build());
//        iniNotification();


    }

    private void iniNotification(String title, String subtitle) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_arrow_back_white_24dp)
                .setContentTitle(title)
                .setContentText(subtitle)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test channel";
            String description = "this is for test purpose";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}