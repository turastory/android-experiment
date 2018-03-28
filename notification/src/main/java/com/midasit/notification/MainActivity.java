package com.midasit.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
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
        
        findViewById(R.id.button).setOnClickListener(v -> Alarm.set(this, alarmHour, alarmMinute, alarmSecond));
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
