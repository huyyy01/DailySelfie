package com.example.dailycame;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 1;

    // Notification action elements
    private Intent mNotificationIntent;
    private PendingIntent mPendingIntent;

    // Notification sound and vibration on arrival
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            mNotificationIntent = new Intent(context, MainActivity.class);
            mPendingIntent = PendingIntent.getActivity(context, 0, mNotificationIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_CANCEL_CURRENT);

            // Build notification

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "alarm")
                    .setTicker("Time for another selfie")
                    .setSmallIcon(R.drawable.ic_camera)
                    .setAutoCancel(true)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText("Your alarm goes here")
                    .setContentIntent(mPendingIntent);

            String channelId = "ALARM";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Your alarm is here",
                    NotificationManager.IMPORTANCE_HIGH);
            // Get NotificationManager
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            Toast.makeText(context, "Notification", Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            Log.d("NOTIFICATION", exception.getMessage().toString());
        }
    }
}