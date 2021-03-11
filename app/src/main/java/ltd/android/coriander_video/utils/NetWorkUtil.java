package ltd.android.coriander_video.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ltd.android.coriander_video.app.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetWorkUtil {

  /**
   * 检测网络是否连接
   */
  public static boolean isNetConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm != null) {
      NetworkInfo[] infos = cm.getAllNetworkInfo();
      if (infos != null) {
        for (NetworkInfo ni : infos) {
          if (ni.isConnected()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * 检测wifi是否连接
   */
  public static boolean isWifiConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm != null) {
      NetworkInfo networkInfo = cm.getActiveNetworkInfo();
      return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
    return false;
  }

  /**
   * 检测3G是否连接
   */
  public static boolean is3gConnected(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (cm != null) {
      NetworkInfo networkInfo = cm.getActiveNetworkInfo();
      return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }
    return false;
  }

  /**
   * 判断网址是否有效
   */
  public static boolean isLinkAvailable(String link) {
    Pattern pattern = Pattern.compile(
        "^(http://|https://)?((?:[A-Za-z0-9]+-[A-Za-z0-9]+|[A-Za-z0-9]+)\\.)+([A-Za-z]+)[/\\?\\:]?.*$",
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(link);
    return matcher.matches();
  }

  /**
   * 判断网络是否有效
   *
   * @param context 上下文
   * @return 返回结果
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivity = (ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivity == null) {
      return false;
    } else {
      NetworkInfo[] info = connectivity.getAllNetworkInfo();
      if (info != null) {
        for (NetworkInfo anInfo : info) {
          if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    }
    return false;
  }


  public static boolean isLanConnected() {
    if (App.getAppContext() == null) {
      return false;
    }

    ConnectivityManager connectivityManager = (ConnectivityManager)
        App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    return (activeNetInfo != null)
        && (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI
        || activeNetInfo.getType() == 7  //TYPE_BLUETOOTH，没有公开，x86虚拟机使用
        || activeNetInfo.getType() == 9) //TYPE_ETHERNET，没有公开，可能被使用？
        && (activeNetInfo.isConnected());
  }

  public static boolean isMobileConnected() {
    if (App.getAppContext() == null) {
      return false;
    }

    ConnectivityManager connectivityManager = (ConnectivityManager)
        App.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    return (activeNetInfo != null)
        && (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE)
        && (activeNetInfo.isConnected());
  }

  /**
   * 判断网络是否可用
   */
  public static boolean isAvailableConnected(Context context, int NetWorkType) {
    if (context != null) {
      ConnectivityManager mConnectivityManager = (ConnectivityManager) context
          .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mNetworkInfo = mConnectivityManager
          .getNetworkInfo(NetWorkType);
      if (mNetworkInfo != null) {
        return mNetworkInfo.isAvailable();
      }
    }
    return false;
  }

}
