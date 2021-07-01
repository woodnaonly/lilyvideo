package ltd.android.coriander_video.adapter.view_pager_adapter

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

import java.util.*

/**
 * create by libo
 * create on 2020/5/19
 * description 公共viewPageradapter
 */
class CommPagerAdapter(
    fm: androidx.fragment.app.FragmentManager?,
    private val items: ArrayList<out androidx.fragment.app.Fragment>,
    private val mTitles: Array<String>
) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return if (items.size == 0) 0 else items.size
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return items[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun saveState(): Parcelable? {
        return null
    }
}