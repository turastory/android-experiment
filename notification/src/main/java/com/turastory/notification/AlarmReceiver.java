package com.turastory.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Receives alarm and make a notification
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("asdf", "Receive Intent...");
    
        Intent newIntent = new Intent(context, OnWakeActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(context, 100, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notification")
            .setContentIntent(contentIntent)
            .setSmallIcon(android.R.drawable.ic_menu_camera)
            .setContentTitle("Title!")
            .setContentText("Text!")
            .setAutoCancel(true);
        
        notificationManager.notify(100, builder.build());
    }
}
