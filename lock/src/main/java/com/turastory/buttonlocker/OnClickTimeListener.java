package com.turastory.buttonlocker;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by soldi on 2018-03-08.
 * <p>
 * 일정 시간 내에 연속으로 발생하는 클릭을 막아주는 리스너.
 */

public abstract class OnClickTimeListener extends OnClickListenerProxy {

    private final long minimumInterval;
    private long lastClickTime;

    /**
     * @param minimumIntervalInMs 허용되는 버튼 클릭 간격
     */
    public OnClickTimeListener(long minimumIntervalInMs) {
        this.minimumInterval = minimumIntervalInMs;
        this.lastClickTime = 0;
    }

    public OnClickTimeListener() {
        this(1000);
    }

    @Override
    public void onClick(View clickedView) {
        long currentTimestamp = SystemClock.uptimeMillis();

        if (Math.abs(currentTimestamp - lastClickTime) > minimumInterval) {
            // 실제 클릭이 발생할 때만 클릭 시간을 업데이트한다.
            lastClickTime = currentTimestamp;
            onClickProxy(clickedView);
        }
    }
}
