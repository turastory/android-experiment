package com.turastory.progress_management;

import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by soldi on 2018-03-08.
 * <p>
 * Represent a request call.
 */

public class Call extends Thread implements Cancellable {
    
    public interface OnFinishListener {
        void onFinish();
    }
    
    private boolean flag = false;

    private WeakReference<MainActivity> mainActivityWeak;
    private int time;
    private boolean success;
    
    private OnFinishListener onFinishListener;

    public Call(MainActivity mainActivity, int time, boolean success) {
        this.mainActivityWeak = new WeakReference<>(mainActivity);
        this.time = time;
        this.success = success;
    }
    
    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }
    
    @Override
    public void run() {
        int count = 0;

        while (!flag && count < this.time) {
            count += 10;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.e("asdf", "Thread stop");
        if (mainActivityWeak.get() != null)
            mainActivityWeak.get().removeCall(this);

        if (flag) {
            Log.e("asdf", "Cancel");
        } else if (success) {
            Log.e("asdf", "Success");
        } else {
            Log.e("asdf", "Failure");
        }
        
        if (onFinishListener != null) {
            onFinishListener.onFinish();
        }
    }

    @Override
    public void cancel() {
        Log.e("asdf", "Request cancel a call.");
        flag = true;
    }

    @Override
    public boolean isCanceled() {
        return flag;
    }
}
