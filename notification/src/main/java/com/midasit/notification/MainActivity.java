package com.midasit.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by nyh0111 on 2018-03-28.
 */

public class MainActivity extends AppCompatActivity {
    
    // Wake up the device to fire the alarm at precisely 8:30 a.m., and every 20 minutes thereafter:
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button).setOnClickListener(v -> registerAlarm());
    }
    
    private void registerAlarm() {
        Log.e("asdf", "Register alarm");
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
    
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
    
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants—in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

//        // setRepeating() lets you specify a precise custom interval—in this case,
//        // 20 minutes.
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//            1000 * 60 * 20, alarmIntent);
    }
}
