package ltd.android.coriander_video.utils.glide.Transformation;

/**
 * Copyright (C) 2018 Wasabeef <p> Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at <p> http://www.apache.org/licenses/LICENSE-2.0 <p> Unless required by applicable law
 * or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.internal.FastBlur;

import java.security.MessageDigest;

public class GlideBlurTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.BlurTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private static int MAX_RADIUS = 25;
  private static int DEFAULT_DOWN_SAMPLING = 1;

  private int radius;
  private int sampling;

  public GlideBlurTransformation() {
    this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public GlideBlurTransformation(int radius) {
    this(radius, DEFAULT_DOWN_SAMPLING);
  }

  public GlideBlurTransformation(int radius, int sampling) {
    this.radius = radius;
    this.sampling = sampling;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
      @NonNull Bitmap toTransform, int outWidth, int outHeight) {

    int width = toTransform.getWidth();
    int height = toTransform.getHeight();
    int scaledWidth = width / sampling;
    int scaledHeight = height / sampling;

    Bitmap bitmap = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    canvas.scale(1 / (float) sampling, 1 / (float) sampling);
    Paint paint = new Paint();
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(toTransform, 0, 0, paint);

    bitmap = FastBlur.blur(bitmap, radius, true);

    return bitmap;
  }

  @Override
  public String toString() {
    return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof GlideBlurTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode();
  }

  @Override
  public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
