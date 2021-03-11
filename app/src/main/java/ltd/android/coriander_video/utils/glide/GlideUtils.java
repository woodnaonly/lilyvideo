package ltd.android.coriander_video.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import jp.wasabeef.glide.transformations.BlurTransformation;
import ltd.android.coriander_video.R;
import ltd.android.coriander_video.utils.ResourcesUtils;
import ltd.android.coriander_video.utils.glide.Transformation.GlideBorderTransform;
import ltd.android.coriander_video.utils.glide.Transformation.GlideInvoiceMarkTransform;
import ltd.android.coriander_video.utils.glide.Transformation.GlideRoundTransform;


/**
 * debug模式下没有硬盘缓存
 */
public class GlideUtils {


  public static boolean isDestroy(Activity activity) {
    if (activity == null || activity.isFinishing() || (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
      return true;
    } else {
      return false;
    }
  }


  public static void loadImgCircle(ImageView v, String url) {
    Context context = v.getContext();
    GlideApp.with(context).load(url).apply(requestOptions())
//                .transform(new GlideCircleTransform(context, 2, ResourcesUtils.getColor(R.color.home_item_line)))
        .into(v);

  }

  public static void loadImgBorder(ImageView v, String url) {
    Context context = v.getContext();
    GlideApp.with(context).load(url).apply(requestOptions()).transform(
        new GlideBorderTransform(context, 1, ResourcesUtils.getColor(R.color.home_item_line)))
        .transform(new GlideRoundTransform(context))
        .into(v);

  }

  public static void loadImgRound(ImageView v, String url, int radius) {
    Context context = v.getContext();
    GlideApp.with(context).load(url).apply(requestOptions()).transform(
        new GlideRoundTransform(context, radius))
        .into(v);

  }

  public static void loadImg(ImageView v, String url) {
    GlideApp.with(v.getContext()).load(url).apply(requestOptions()).into(v);

  }


  public static void loadInvoiceMark(ImageView v, String url) {
    GlideApp.with(v.getContext()).load(url).apply(requestOptions())
        .transform(new GlideInvoiceMarkTransform(10, Color.RED))
        .into(v);

  }


  public static void loadGaussImg(ImageView v, String url) {
    //加载背景，
    GlideApp.with(v.getContext()).load(url).apply(requestOptions())
        .transform(new BlurTransformation(20, 35))
//                .transform(new GlideBlurTransformation(20, 35))
        .into(v);

  }

  public static void loadImg(ImageView v, @DrawableRes int url) {
    GlideApp.with(v.getContext()).load(url).apply(requestOptions()).into(v);
  }


  public static void loadUserAvatar(ImageView v, String url) {
    GlideApp.with(v.getContext()).load(url).centerInside().placeholder(R.drawable.ic_no_login_head)
        .error(R.drawable.ic_no_login_head).into(v);
  }


  public static RequestOptions requestOptions() {
//    if (BuildConfig.DEBUG) {
//      return new RequestOptions().placeholder(R.drawable.ic_loading_pic)
//          .error(R.drawable.ic_loading_pic).priority(Priority.HIGH)
//          .diskCacheStrategy(DiskCacheStrategy.NONE);
//    }
    return new RequestOptions().placeholder(R.drawable.ic_loading_pic)
        .error(R.drawable.ic_loading_pic).priority(Priority.HIGH)
        .diskCacheStrategy(DiskCacheStrategy.ALL);
  }

}
