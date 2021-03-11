package ltd.android.coriander_video.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class IndexViewPager extends ViewPager {
    /* renamed from: a */
    private boolean f523a = false;

    public IndexViewPager(Context context) {
        super(context);
    }

    public IndexViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setScanScroll(boolean z) {
        this.f523a = z;
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.f523a ? super.onTouchEvent(motionEvent) : false;
    }

    public void setCurrentItem(int i, boolean z) {
        super.setCurrentItem(i, z);
    }

    public void setCurrentItem(int i) {
        super.setCurrentItem(i);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f523a ? super.onInterceptTouchEvent(motionEvent) : false;
    }
}
