package ltd.android.coriander_video.utils;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;
import ltd.android.coriander_video.app.App;


public class ToastUtil {
    private static volatile ToastUtil sToastUtil = null;

    private Toast mToast = null;


    public static ToastUtil getInstance() {
        if (sToastUtil == null) {
            synchronized (ToastUtil.class) {
                if (sToastUtil == null) {
                    sToastUtil = new ToastUtil();
                }
            }
        }
        return sToastUtil;
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容 持续时间默认为short
     *
     * @param tips 要显示的内容 {@link Toast#LENGTH_LONG}
     */

    public void showToast(final String tips) {
        showToast(tips, Toast.LENGTH_SHORT);
    }

    public void showToast(final int tips) {
        showToast(tips, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param tips     要显示的内容
     * @param duration 持续时间，参见{@link Toast#LENGTH_SHORT}和 {@link Toast#LENGTH_LONG}
     */
    public void showToast(final String tips, final int duration) {
        if (TextUtils.isEmpty(tips) || TextUtils.equals(tips.toLowerCase(), "null")) {
            return;
        }
        handler.post(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(App.getAppContext(), tips, duration);
                mToast.show();
            } else {
                mToast.setText(tips);
                mToast.setDuration(duration);
                mToast.show();
            }
        });
    }

    public void showToast(@StringRes final int tips, final int duration) {
        handler.post(() -> {
            if (mToast == null) {
                mToast = Toast.makeText(App.getAppContext(), tips, duration);
                mToast.show();
            } else {
                mToast.setText(tips);
                mToast.setDuration(duration);
                mToast.show();
            }
        });
    }
}
