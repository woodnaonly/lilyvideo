package ltd.android.coriander_video.view

import android.content.Context
import android.util.AttributeSet

/**
 * create by libo
 * create on 2020-05-21
 * description 跑马灯textview
 */
class MarqueeTextView : android.support.v7.widget.AppCompatTextView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    override fun isFocused(): Boolean {
        return true
    }
}