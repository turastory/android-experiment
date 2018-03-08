package com.midasit.buttonlocker;

import android.os.SystemClock;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by nyh0111 on 2018-03-08.
 */

public abstract class OnClickListenerProxy implements View.OnClickListener {
    
    private final long minimumInterval;
    private Map<View, Long> lastClickMap;
    
    /**
     * Implement this in your subclass instead of onClick
     * @param v The view that was clicked
     */
    public abstract void onDebouncedClick(View v);
    
    /**
     * The one and only constructor
     * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
     */
    public OnClickListenerProxy(long minimumIntervalMsec) {
        this.minimumInterval = minimumIntervalMsec;
        this.lastClickMap = new WeakHashMap<View, Long>();
    }
    
    @Override public void onClick(View clickedView) {
        Long previousClickTimestamp = lastClickMap.get(clickedView);
        long currentTimestamp = SystemClock.uptimeMillis();
        
        lastClickMap.put(clickedView, currentTimestamp);
        if(previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp) > minimumInterval) {
            onDebouncedClick(clickedView);
        }
    }
}
