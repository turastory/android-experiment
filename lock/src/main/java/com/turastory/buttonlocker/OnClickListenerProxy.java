package com.turastory.buttonlocker;

import android.view.View;

/**
 * Created by tura on 2018-03-08.
 * <p>
 * OnClickListener를 감싸는 일종의 Proxy 리스너.
 * 선처리/후처리가 필요한 상황에 유용하다.
 */

public abstract class OnClickListenerProxy implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        onClickProxy(v);
    }

    public abstract void onClickProxy(View v);
}
