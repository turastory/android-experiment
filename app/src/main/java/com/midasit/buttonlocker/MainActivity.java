package com.midasit.buttonlocker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    
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
        
//        timelyButton.setOnClickListener(new OnClickListenerProxy() {
//            @Override
//            public void onClick(View v) {
//                if (isLock(v))
//                    return;
//
//                lock(v);
//
//                Log.e("asdf", "Run!!");
//
//                handler.postDelayed(() -> release(v), 1000);
//            }
//        });
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
