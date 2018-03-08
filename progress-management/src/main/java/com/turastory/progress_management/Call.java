package com.turastory.progress_management;

import android.util.Log;

/**
 * Created by soldi on 2018-03-08.
 * <p>
 * Represent a request call.
 */

public class Call extends Thread implements Cancellable {

    private boolean flag = false;

    private int time;
    private Runnable runnable;

    public Call(int time, Runnable runnable) {
        this.time = time;
        this.runnable = runnable;
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
        runnable.run();
    }

    @Override
    public void cancel() {
        Log.e("asdf", "Cancel a call.");
        flag = true;
    }

    @Override
    public boolean isCanceled() {
        return flag;
    }
}
