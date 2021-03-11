package ltd.android.coriander_video.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author by 梁馨 on 2019/3/26.
 */
public class FileManage {
    public static final String appName = "coriander_video";

    public static String tempDirPath() {
        return tempDir().getAbsolutePath();
    }

    public static File appDir() {
        File file = new File(Environment.getExternalStorageDirectory(),
                appName);
        if (!file.exists()) {
            file.mkdirs();
        }


        return file;
    }

    public static File tempDir() {
        File file = new File(appDir(),
                "temp");
        if (!file.exists()) {
            file.mkdirs();
        }


        return file;
    }

    public static File hearCrop() {
        return new File(tempDir(), "hearCrop.jpg");
    }

    public static File screenshot() {
        return new File(appDir(), "screenshot.png");
    }

    public static File GlideCache() {
        File file = new File(appDir(), "glide");
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    public static String GlideCacheSize() {
        return FileUtils.getDirSize(GlideCache());
    }
}
