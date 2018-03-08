package com.midasit.buttonlocker;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nyh0111 on 2018-03-08.
 */

public abstract class BaseActivity extends AppCompatActivity {
    
    private ConcurrentHashMap<View, Boolean> lockMap = new ConcurrentHashMap<>();
    
    protected void lock(View view) {
        lockMap.put(view, true);
    }
    
    protected void release(View view) {
        lockMap.remove(view);
    }
    
    protected boolean isLock(View view) {
        Boolean lock = lockMap.get(view);
        
        return lock != null && lock;
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        
        lockMap.clear();
    }
}
