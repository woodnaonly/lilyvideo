package ltd.android.coriander_video.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import ltd.android.coriander_video.app.App;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/1
 *     desc  : 设备相关工具类
 * </pre>
 */
public final class DeviceUtils {

  private DeviceUtils() {
    throw new UnsupportedOperationException("u can't instantiate me...");
  }


  /**
   * 获取设备系统版本号
   *
   * @return 设备系统版本号
   */
  public static int getSDKVersion() {
    return Build.VERSION.SDK_INT;
  }


  /**
   * 获取设备AndroidID
   *
   * @return AndroidID
   */
  @SuppressLint("HardwareIds")
  public static String getAndroidID() {
    return Settings.Secure
        .getString(App.getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
  }

  /**
   * 获取设备MAC地址 <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
   * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
   *
   * @return MAC地址
   */
  public static String getMacAddress() {
    String macAddress = getMacAddressByWifiInfo();
    if (!"02:00:00:00:00:00".equals(macAddress)) {
      return macAddress;
    }
    macAddress = getMacAddressByNetworkInterface();
    if (!"02:00:00:00:00:00".equals(macAddress)) {
      return macAddress;
    }

    return null;
  }

  /**
   * 获取设备MAC地址 <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
   *
   * @return MAC地址
   */
  @SuppressLint("HardwareIds")
  private static String getMacAddressByWifiInfo() {
    try {
      @SuppressLint("WifiManagerLeak") WifiManager wifi = (WifiManager) App.getAppContext()
          .getSystemService(Context.WIFI_SERVICE);
      if (wifi != null) {
        WifiInfo info = wifi.getConnectionInfo();
        if (info != null) {
          return info.getMacAddress();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "02:00:00:00:00:00";
  }

  /**
   * 获取设备MAC地址 <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
   *
   * @return MAC地址
   */
  private static String getMacAddressByNetworkInterface() {
    try {
      List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface ni : nis) {
        if (!ni.getName().equalsIgnoreCase("wlan0")) {
          continue;
        }
        byte[] macBytes = ni.getHardwareAddress();
        if (macBytes != null && macBytes.length > 0) {
          StringBuilder res1 = new StringBuilder();
          for (byte b : macBytes) {
            res1.append(String.format("%02x:", b));
          }
          return res1.deleteCharAt(res1.length() - 1).toString();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "02:00:00:00:00:00";
  }


  /**
   * 获取设备厂商 <p>如Xiaomi</p>
   *
   * @return 设备厂商
   */

  public static String getManufacturer() {
    return Build.MANUFACTURER;
  }

  /**
   * 获取设备型号 <p>如MI2SC</p>
   *
   * @return 设备型号
   */
  public static String getModel() {
    String model = Build.MODEL;
    if (model != null) {
      model = model.trim().replaceAll("\\s*", "");
    } else {
      model = "";
    }
    return model;
  }

  @SuppressLint("MissingPermission")
  /**
   * 获取IMEI
   */ public static String getIMEI() {
    String IMEI = null;
    TelephonyManager telephonyManager = (TelephonyManager) App.getAppContext()
        .getSystemService(Context.TELEPHONY_SERVICE);
    try {
      IMEI = telephonyManager.getDeviceId();
      return IMEI;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }

  }


}
