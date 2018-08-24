package com.edeals.dist.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.edeals.dist.R;
import com.edeals.dist.activities.HomeActivity;

import java.util.Map;

/**
 * Created by team edeals on 07-03-2018.
 */

public class MessagingService extends FirebaseMessagingService {

    public static String TAG = "MyFirebaseMessagingService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Map<String, String> remoteMessageData = remoteMessage.getData();

        Log.d(TAG, "From => " + from);

        // check if the message contains data
        if (remoteMessageData.size() > 0) {
            Log.d(TAG, "Remote Message Data => " + remoteMessageData);
            sendNotification(remoteMessageData.get("message"));
        }

        // check if the message contains notification
        if (remoteMessage.getNotification() != null) {
            String remoteMessageBody = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Remote Message Body => " + remoteMessageBody);
//            sendNotification(remoteMessageBody);
        }
    }

    private void sendNotification(String remoteMessageBody) {
        Intent main = new Intent(this, HomeActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, main, PendingIntent.FLAG_ONE_SHOT);
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.launcher)
                .setContentTitle("New notification from edeals")
                .setContentText(remoteMessageBody)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
