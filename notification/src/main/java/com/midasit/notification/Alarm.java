package com.midasit.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Util class for setting an alarm.
 */
public class Alarm {
    
    public static final int DEFAULT_INTERVAL = 1000 * 60;
    
    public static void set(Context context, int hour, int minute, int second, int interval) {
        Log.e("asdf", "Register alarm at " + String.format("%02d:%02d:%02d", hour, minute, second));
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        
        AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
        
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                interval, alarmIntent);
        }
    }
    
    public static void set(Context context, int hour, int minute, int second) {
        set(context, hour, minute, second, DEFAULT_INTERVAL);
    }
}
