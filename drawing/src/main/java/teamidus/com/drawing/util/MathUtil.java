package teamidus.com.drawing.util;

/**
 * Created by nyh0111 on 2018-04-05.
 *
 * Handy util class for providing methods which java.lang.Math not provides.
 */
public class MathUtil {
    public static float clamp(float target, float min, float max) {
        return Math.max(min, Math.min(max, target));
    }
}
