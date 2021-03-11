package ltd.android.coriander_video.utils;

import android.text.TextUtils;
import ltd.android.coriander_video.utils.sharedpreference.SystemPrefsHelper;

import java.util.UUID;


public class UUIDS {

  public static String getUUID() {
    SystemPrefsHelper prefsHelper = SystemPrefsHelper.getInstance();
    String uuid = prefsHelper.getDeviceId();
    if (!TextUtils.isEmpty(uuid)) {
      return uuid;
    }
    if (TextUtils.isEmpty(uuid)) {
      uuid = DeviceUtils.getIMEI();
    }
    if (TextUtils.isEmpty(uuid)) {
      uuid = DeviceUtils.getAndroidID();
    }
    if (TextUtils.isEmpty(uuid)) {
      uuid = DeviceUtils.getMacAddress();
    }
    UUID UUID = new UUID(uuid.hashCode(), uuid.hashCode() << 2);
    uuid = UUID.toString();
    prefsHelper.setDeviceId(uuid);
    return uuid;


  }


}
