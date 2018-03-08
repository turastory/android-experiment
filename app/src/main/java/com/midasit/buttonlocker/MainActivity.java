package com.midasit.buttonlocker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    
    private static final int MSG_CALLBACK = 1;
    
    @BindView(R.id.timely_button)
    Button timelyButton;
    @BindView(R.id.callback_button)
    Button callbackButton;
    
    private Handler handler;
    private Map<View, Long> timelyLock = new HashMap<>();
    private Map<View, AtomicBoolean> manualLock = new HashMap<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
        handler = new MyHandler();
        
        registerTimelyButton(timelyButton);
        
        timelyButton.setOnClickListener(v -> {
        });
        
        timelyButton.setOnClickListener(new OnClickListenerProxy() {
            @Override
            public void onClick(View v) {
            
            }
        });
        
        callbackButton.setOnClickListener(v -> {
            handler.sendEmptyMessageDelayed(MSG_CALLBACK, 1000);
        });
    }
    
    protected void registerTimelyButton(View view) {
        if (!view.isClickable()) return;
    
        Log.e("asdf", "Register " + view.getTag());
        
        view.setOnClickListener(v -> {});
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        timelyLock.clear();
        manualLock.clear();
    }
    
    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CALLBACK:
                    break;
            }
        }
    }
}
