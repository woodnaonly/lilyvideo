package ltd.android.coriander_video.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import ltd.android.coriander_video.activity_lifecycle_callback.SwitchBackgroundCallbacks;
import ltd.android.coriander_video.utils.ObjectBox;


public class App extends Application {

    private static App app;
    private SwitchBackgroundCallbacks mSwitchBackgroundCallbacks;

    public static App getInstance() {
        return app;
    }

    public static App getAppContext() {
        return app;
    }


    public  SwitchBackgroundCallbacks getSwitchBackgroundCallbacks() {
        return mSwitchBackgroundCallbacks;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
//    MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//    MultiDex.install(this);
        app = this;
        ObjectBox.INSTANCE.init(this);
        initActivityLifecycle();
    }


    private void setStrictMode() {
        StrictMode
                .setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                        .build());
    }


    private void initActivityLifecycle() {
        mSwitchBackgroundCallbacks = new SwitchBackgroundCallbacks();
        registerActivityLifecycleCallbacks(mSwitchBackgroundCallbacks);
    }

    public void stopActivity(Class... classes) {
        mSwitchBackgroundCallbacks.stopActivity(classes);
    }

    /***
     * 关闭注册
     */
    public void stopSigin() {

    }

    /***
     * 关闭登录
     */
    public void stopLogin() {
//        stopActivity()
    }


    public void stopAllActivity() {
        mSwitchBackgroundCallbacks.stopAllActivity();
    }

    public Activity getCurActivity() {
        return mSwitchBackgroundCallbacks.getCurActivity();
    }


}
