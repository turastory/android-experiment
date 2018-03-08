package com.turastory.buttonlocker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    
    private static final int MSG_CALLBACK = 1;
    
    @BindView(R.id.timely_button)
    Button timelyButton;
    @BindView(R.id.callback_button)
    Button callbackButton;
    
    private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        
        handler = new MyHandler();

        timelyButton.setOnClickListener(new OnClickTimeListener() {
            @Override
            public void onClickProxy(View v) {
                Log.e("asdf", "Run time!!!");
            }
        });

        callbackButton.setOnClickListener(new OnClickCallbackListener() {
            @Override
            public void onClickProxy(View v) {
                Log.e("asdf", "Run callback!!!");
                handler.postDelayed(this::unlock, 1000);
            }
        });
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
