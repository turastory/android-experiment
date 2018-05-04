package com.turastory.security;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by tura on 2018-05-04.
 */
public class Exec {
    
    private static Executor mainThreadExecutor = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        
        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    };
    
    private static Executor backgroundExecutor = Executors.newFixedThreadPool(3);
    
    public static Executor main() {
        return mainThreadExecutor;
    }
    
    public static Executor backgronud() {
        return backgroundExecutor;
    }
}
