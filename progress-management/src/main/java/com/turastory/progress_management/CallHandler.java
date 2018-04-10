package com.turastory.progress_management;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tura on 2018-03-21.
 *
 * Handle sequential calls..
 */

public class CallHandler extends Handler {
    
    public static final int NEXT = 0;
    
    private final Object object = new Object();
    private final MainActivity activity;
    
    private BlockingQueue<Call> sequentialCalls = new LinkedBlockingQueue<>();
    
    public CallHandler(MainActivity activity) {
        this.activity = activity;
    }
    
    public void enqueue(Call call) {
        synchronized (object) {
            Log.e("asdf", "enqueue " + call.getName());
            sequentialCalls.add(call);
            
            if (sequentialCalls.size() == 1) {
                next();
            }
        }
    }
    
    private void next() {
        Message message = obtainMessage();
        message.what = NEXT;
        sendMessage(message);
    }
    
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case NEXT:
                startNextCall();
                break;
        }
    }
    
    private void startNextCall() {
        synchronized (object) {
            if (sequentialCalls.size() > 0) {
                Call call = sequentialCalls.remove();
                call.setOnFinishListener(this::next);
                activity.addCall(call);
            }
        }
    }
}
