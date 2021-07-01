package ltd.android.coriander_video.utils.glide.Transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;


public class GlideBorderTransform extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.CropSquareTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);


  private Paint mBorderPaint;
  private float mBorderWidth;

  public GlideBorderTransform(Context context) {
  }

  public GlideBorderTransform(Context context, int borderWidth, int borderColor) {
    mBorderWidth = borderWidth;

    mBorderPaint = new Paint();
    mBorderPaint.setDither(true);
    mBorderPaint.setAntiAlias(true);
    mBorderPaint.setColor(borderColor);
    mBorderPaint.setStyle(Paint.Style.STROKE);
    mBorderPaint.setStrokeWidth(mBorderWidth);
  }


  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    return BorderCrop(pool, toTransform, outWidth, outHeight);
//        return null;
  }

  private Bitmap BorderCrop(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {
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
      canvas.drawRect(0, 0, source.getWidth(), source.getHeight(), mBorderPaint);
    }
    return result;
  }


  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}