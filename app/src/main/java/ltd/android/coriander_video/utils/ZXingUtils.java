package ltd.android.coriander_video.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author by 黄梦 on 2019/3/28.
 */
public class ZXingUtils {

    /* renamed from: a */
    private static int f11129a = 50;

    /* renamed from: a */
    public static Bitmap StringToBitMap(String str, int size, Bitmap bitmap) {
        try {
            f11129a = size / 10;
            Map hashtable = new Hashtable();
            hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            BitMatrix bitMatrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, size, size, hashtable);
            int width = bitMatrix.getWidth();
            int i2 = width / 2;
            int height = bitMatrix.getHeight() / 2;
            Matrix matrix = new Matrix();
            matrix.setScale((((float) f11129a) * 2.0f) / ((float) bitmap.getWidth()), (2.0f * ((float) f11129a)) / ((float) bitmap.getHeight()));
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            int[] iArr = new int[(size * size)];
            int i3 = 0;
            while (i3 < size) {
                int i4 = 0;
                while (i4 < size) {
                    if (i4 > i2 - f11129a && i4 < f11129a + i2 && i3 > height - f11129a && i3 < f11129a + height) {
                        iArr[(i3 * width) + i4] = bitmap.getPixel((i4 - i2) + f11129a, (i3 - height) + f11129a);
                    } else if (bitMatrix.get(i4, i3)) {
                        iArr[(i3 * size) + i4] = ViewCompat.MEASURED_STATE_MASK;
                    } else {
                        iArr[(i3 * size) + i4] = -1;
                    }
                    i4++;
                }
                i3++;
            }
            Bitmap createBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            createBitmap.setPixels(iArr, 0, size, 0, 0, size, size);
            return createBitmap;
        } catch (WriterException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
