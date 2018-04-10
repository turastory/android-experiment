package com.turastory.drawing.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by tura on 2017-06-29.
 */

public class DpConverter {
    public static int convertDpToPixel(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
