package ltd.android.coriander_video.activity_lifecycle_callback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * @author by 梁馨 on 2018/6/20.
 */
public class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

    private int mFinalCount;
    private Stack<Activity> mActivityStack = new Stack<>();
    private Boolean isRunInBackground = false;

    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    public Activity getCurActivity() {
        return mActivityStack.lastElement();
    }


    public void stopAllActivity() {
        for (int i = 0; i < mActivityStack.size(); i++) {
            mActivityStack.get(i).finish();
        }
    }

    public void stopActivity(Class... classes) {
        for (int i = 0; i < mActivityStack.size(); i++) {
            for (Class aClass : classes) {
                Activity activity = mActivityStack.get(i);
                if (activity.getClass().getSimpleName().equals(aClass.getSimpleName())) {
                    activity.finish();
                }
            }
        }
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivityStack.add(activity);
    }


    @Override
    public void onActivityStarted(Activity activity) {
        mFinalCount++;
        if (mFinalCount == 1) {
            back2App(activity);
        }
    }


    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mFinalCount--;
        if (mFinalCount == 0) {
            //说明从前台回到了后台
            leaveApp(activity);
        }
    }

    private void leaveApp(Activity activity) {
        isRunInBackground = true;

    }

    private void back2App(Activity activity) {
        isRunInBackground = false;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivityStack.remove(activity);
    }


}
