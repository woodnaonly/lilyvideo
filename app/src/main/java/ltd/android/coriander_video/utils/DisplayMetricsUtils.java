package ltd.android.coriander_video.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import ltd.android.coriander_video.app.App;

public class DisplayMetricsUtils {
    private static final Context mContext = App.getAppContext();
    private static DisplayMetrics mDisplayMetrics = mContext.getResources().getDisplayMetrics();

    public static float getWindowHeight() {
        return (float) mDisplayMetrics.heightPixels;
    }

    public static float getWindowWidth() {
        return (float) mDisplayMetrics.widthPixels;
    }

    public static float dp2px(float f) {
        return (f * mDisplayMetrics.density) + 0.5f;
    }


}
