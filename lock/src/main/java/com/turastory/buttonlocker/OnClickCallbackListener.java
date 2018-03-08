package com.turastory.buttonlocker;

import android.view.View;

/**
 * Created by soldi on 2018-03-08.
 * <p>
 * 실행과 동시에 lock이 걸리고, 원하는 시점에 직접 lock을 풀어줄 수 있는 리스너.
 */

public abstract class OnClickCallbackListener extends OnClickListenerProxy {

    private boolean lock;

    @Override
    public void onClick(View clickedView) {
        if (lock) return;

        lock = true;

        onClickProxy(clickedView);
    }

    protected void unlock() {
        lock = false;
    }
}
