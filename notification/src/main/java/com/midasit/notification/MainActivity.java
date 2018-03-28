package com.midasit.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nyh0111 on 2018-03-28.
 */

public class MainActivity extends AppCompatActivity {
    
    private TextView textView;
    
    // Wake up the device to fire the alarm at precisely 8:30 a.m., and every 20 minutes thereafter:
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    
    private int alarmHour;
    private int alarmMinute;
    private int alarmSecond;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        
        alarmHour = calendar.get(Calendar.HOUR_OF_DAY);
        alarmMinute = calendar.get(Calendar.MINUTE);
        alarmSecond = calendar.get(Calendar.SECOND);
        
        textView = findViewById(R.id.text);
        
        findViewById(R.id.button).setOnClickListener(v -> registerAlarm(alarmHour, alarmMinute, alarmSecond, 0));
        findViewById(R.id.button2).setOnClickListener(v -> registerAlarm(alarmHour, alarmMinute, alarmSecond, 1));
        findViewById(R.id.cancel_button).setOnClickListener(v -> cancelAlarm());
        findViewById(R.id.increment_button).setOnClickListener(v -> increment());
        
        bindView();
    }
    
    private void cancelAlarm() {
        if (alarmIntent != null) {
            alarmManager.cancel(alarmIntent);
            alarmIntent = null;
        }
    }
    
    private void registerAlarm(int hour, int minute, int second, int requestCode) {
        if (alarmIntent != null) {
            Log.e("asdf", "Alarm is already set. please cancel it first.");
            return;
        }
        
        Log.e("asdf", "Register alarm at " + String.format("%02d:%02d:%02d", hour, minute, second));
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);
        
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants—in this case, AlarmManager.INTERVAL_DAY.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            1000, alarmIntent);

//        // setRepeating() lets you specify a precise custom interval—in this case,
//        // 20 minutes.
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//            1000 * 60 * 20, alarmIntent);
    }
    
    private void increment() {
        alarmSecond += 10;
        
        if (alarmSecond >= 60) {
            alarmSecond -= 60;
            alarmMinute += 1;
        }
        
        if (alarmMinute >= 60) {
            alarmMinute -= 60;
            alarmHour += 1;
        }
        
        if (alarmHour >= 24) {
            alarmHour -= 24;
        }
    
        bindView();
    }
    
    private void bindView() {
        textView.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", alarmHour, alarmMinute, alarmSecond));
    }
}
