package com.turastory.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by tura on 2018-03-28.
 */

public class MainActivity extends AppCompatActivity {
    
    private static int counter = 0;
    private static int notification_id = 1;

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
        findViewById(R.id.send_button).setOnClickListener(v -> sendNotification());
        
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
    
    private void sendNotification() {
        Intent intent = new Intent(this, SampleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "my_channel_0")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("This is title")
            .setContentText("This is body This is body This is body This is body This is body This is body This is body This is body")
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setNumber(++counter)
            .setStyle(new NotificationCompat.InboxStyle()
                .setBigContentTitle("I'm Groot!!")
                .addLine("Hello")
                .addLine("My name is")
                .addLine("tura")
                .setSummaryText("Hmm.. this is summary " + counter));
        
        NotificationManager notificationManager =
            (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        notificationManager.notify(notification_id /* ID of notification */, notificationBuilder.build());
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("my_channel_0", "name", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Channel description");
        
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
    
    private void bindView() {
        textView.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", alarmHour, alarmMinute, alarmSecond));
    }
}
