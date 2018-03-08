package com.turastory.progress_management;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by soldi on 2018-03-08.
 */

public class NetworkProgress extends Dialog {
    public NetworkProgress(@NonNull Context context) {
        super(context);
    }

    public NetworkProgress(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NetworkProgress(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);

        Log.e("asdf", "Create progress " + getClass().toString());
    }
}
