package com.turastory.drawing.util;

/**
 * Created by tura on 2018-04-05.
 * <p>
 * Handy util class for providing methods which java.lang.Math not provides.
 */
public class MathUtil {
    public static float clamp(float target, float min, float max) {
        return Math.max(min, Math.min(max, target));
    }
}
