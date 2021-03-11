package ltd.android.coriander_video.utils.sharedpreference;


import android.content.Context;
import android.content.SharedPreferences;
import ltd.android.coriander_video.app.App;

import static ltd.android.coriander_video.utils.sharedpreference.SharedPreferenceKey.*;


public class SystemPrefsHelper {

  private SharedPreferences sp;
  private SharedPreferences.Editor editor;


  private SystemPrefsHelper() {
    sp = App.getAppContext()
        .getSharedPreferences(PREFERENCE_SYSTEM_FILE_NAME, Context.MODE_PRIVATE);
    editor = sp.edit();
  }

  public static SystemPrefsHelper getInstance() {
    return SystemPrefsHelper.Holder.INSTANCE;
  }


  public String getDeviceId() {
    //首次启动这个方法比较特别，默认返回true，而不是一般Boolean的false
    return sp.getString(KEY_DEVICEID, "");
  }

  public void setDeviceId(String DeviceId) {
    editor.putString(KEY_DEVICEID, DeviceId);
    editor.apply();
  }


  private static final class Holder {

    private static final SystemPrefsHelper INSTANCE = new SystemPrefsHelper();
  }
}
