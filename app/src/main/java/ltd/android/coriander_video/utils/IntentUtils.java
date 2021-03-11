package ltd.android.coriander_video.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author by 梁馨 on 2019/3/28.
 */
public class IntentUtils {

    public static void gotoUrl(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
