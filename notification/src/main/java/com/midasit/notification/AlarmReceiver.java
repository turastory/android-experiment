package com.midasit.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by nyh0111 on 2018-03-28.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("asdf", "Receive Intent...");
        
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Intent newIntent = new Intent(context, OnWakeActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    
        PendingIntent contentIntent = PendingIntent.getActivity(context, 100, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
            .setContentIntent(contentIntent)
            .setSmallIcon(android.R.drawable.ic_menu_camera)
            .setContentTitle("Title!")
            .setContentText("Text!")
            .setAutoCancel(true);
        
        notificationManager.notify(100, builder.build());
    }
}
