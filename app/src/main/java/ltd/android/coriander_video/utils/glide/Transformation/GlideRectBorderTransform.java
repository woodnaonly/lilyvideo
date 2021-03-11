package ltd.android.coriander_video.utils.glide.Transformation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA. User: Edward Date: 2018/8/6 Time: 12:50
 */
public class GlideRectBorderTransform extends BitmapTransformation {

  private static final float Border_Width = 4;
  private Paint mBorderPaint;
  private float mBorderWidth;

  public GlideRectBorderTransform(float borderWidth, int borderColor) {
    mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;

    mBorderPaint = new Paint();
    mBorderPaint.setDither(true);
    mBorderPaint.setAntiAlias(true);
    mBorderPaint.setColor(borderColor);
    mBorderPaint.setStyle(Paint.Style.STROKE);
    mBorderPaint.setStrokeWidth(mBorderWidth);
  }


  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
      @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    return circleCrop(pool, toTransform);
  }

  @Override
  public void updateDiskCacheKey(MessageDigest messageDigest) {

  }


  private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    if (source == null) {
      return null;
    }

    int width = source.getWidth();
    int height = source.getHeight();
    Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(
        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);

    canvas.drawRect(0, 0, source.getWidth(), source.getHeight(), paint);

    if (mBorderPaint != null) {
      RectF rectF = new RectF(0, 0, source.getWidth(), source.getHeight());
      canvas.drawRoundRect(rectF, mBorderWidth, mBorderWidth, mBorderPaint);
    }
    return result;
  }


  @Override
  public boolean equals(Object o) {
    return false;
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
