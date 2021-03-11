package ltd.android.coriander_video.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/* renamed from: com.one.cucumber.ui.main.ViewPagerIndicator_Fix */
public class ViewPagerIndicator_Fix extends LinearLayout {
    /* renamed from: a */
    private Paint mPaint;
    /* renamed from: b */
    private int textColorSelected = Color.parseColor("#b4854d");
    /* renamed from: c */
    private int textColorNoSelected = -1;
    /* renamed from: d */
    private int f11848d = 16;
    /* renamed from: e */
    private ViewPager mViewPager;
    /* renamed from: f */
    private List<String> f11850f;
    /* renamed from: g */
    private int f11851g;
    /* renamed from: h */
    private int f11852h;
    /* renamed from: i */
    private int f11853i;
    /* renamed from: j */
    private int f11854j;
    /* renamed from: k */
    private RectF f11855k = new RectF();
    /* renamed from: l */
    private int f11856l;
    /* renamed from: m */
    private float f11857m;

    public void setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public void setTextNoColorSelected(int textColorSelected) {
        this.textColorNoSelected = textColorSelected;
    }
    /* renamed from: com.one.cucumber.ui.main.ViewPagerIndicator_Fix$1 */
    class C26551 implements ViewPager.OnPageChangeListener {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageSelected(int i) {
        }

        C26551() {
        }

        public void onPageScrolled(int i, float f, int i2) {
            ViewPagerIndicator_Fix.this.f11856l = i;
            ViewPagerIndicator_Fix.this.f11857m = f;
            i = Math.round(((float) i) + f);
            for (int i3 = 0; i3 < ViewPagerIndicator_Fix.this.f11851g; i3++) {
                TextView textView = (TextView) ViewPagerIndicator_Fix.this.getChildAt(i3);
                if (i3 == i) {
                    textView.setTextColor(ViewPagerIndicator_Fix.this.textColorSelected);
                } else {
                    textView.setTextColor(ViewPagerIndicator_Fix.this.textColorNoSelected);
                }
            }
            ViewPagerIndicator_Fix.this.postInvalidate();
        }
    }

    /* renamed from: com.one.cucumber.ui.main.ViewPagerIndicator_Fix$2 */
    class C26562 implements OnClickListener {
        C26562() {
        }

        public void onClick(View view) {
            int indexOfChild = ViewPagerIndicator_Fix.this.indexOfChild(view);
            if (ViewPagerIndicator_Fix.this.mViewPager != null) {
                ViewPagerIndicator_Fix.this.mViewPager.setCurrentItem(indexOfChild, true);
            }
        }
    }

    /* renamed from: a */
    private int m15681a(float f) {
        return (int) TypedValue.applyDimension(2, f, getContext().getResources().getDisplayMetrics());
    }

    /* renamed from: b */
    private int m15684b(float f) {
        return (int) TypedValue.applyDimension(1, f, getContext().getResources().getDisplayMetrics());
    }

    /* renamed from: b */
    private void initPaint() {
        this.mPaint = new Paint(1);
        this.mPaint.setTextSize((float) m15681a((float) this.f11848d));
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(this.textColorSelected);
    }

    public ViewPagerIndicator_Fix(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setGravity(16);
        setOrientation(LinearLayout.HORIZONTAL);
        initPaint();
        this.f11854j = m15684b(3.0f);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f11852h = i;
        this.f11853i = i2;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.f11850f != null && this.f11851g > 0) {
            int i = this.f11852h / this.f11851g;
            float f = (float) i;
            float f2 = (float) (i / 2);
            float measureText = (float) (((int) this.mPaint.measureText((String) this.f11850f.get(this.f11856l))) / 6);
            this.f11855k.set((((((float) this.f11856l) + this.f11857m) * f) + f2) - measureText, (float) (this.f11853i - this.f11854j), ((f * (((float) this.f11856l) + this.f11857m)) + f2) + measureText, (float) this.f11853i);
            canvas.drawRect(this.f11855k, this.mPaint);
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        this.mViewPager.addOnPageChangeListener(new C26551());
    }

    public void setTabTitle(List<String> list) {
        if (list != null) {
            this.f11850f = list;
            this.f11851g = list.size();
            for (int i = 0; i < list.size(); i++) {
                addView(mo16955a((String) list.get(i)));
            }
            mo16956a();
        }
    }

    /* renamed from: a */
    public TextView mo16955a(String str) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(2, (float) this.f11848d);
        textView.setTextColor(this.textColorNoSelected);
        textView.setGravity(17);
        textView.setText(str);
        textView.setLayoutParams(new LayoutParams(0, -1, 1.0f));
        return textView;
    }

    /* renamed from: a */
    public void mo16956a() {
        if (this.f11850f != null) {
            for (int i = 0; i < this.f11851g; i++) {
                ((TextView) getChildAt(i)).setOnClickListener(new C26562());
            }
        }
    }
}
