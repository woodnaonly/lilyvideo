package ltd.android.coriander_video.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import ltd.android.coriander_video.app.App;

/**
 * @author by 黄梦 on 2018/1/31.
 */

public class ResourcesUtils {

  public static String getString(@StringRes int resId) {
    return App.getInstance().getResources().getString(resId);
  }

  public static int getColor(@ColorRes int colorId) {
    return ContextCompat.getColor(App.getInstance(), colorId);
  }


  public static Drawable getDrawable(@DrawableRes int drawableId) {
    return ContextCompat.getDrawable(App.getInstance(), drawableId);
  }


}
