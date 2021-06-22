package ltd.android.coriander_video.adapter.view_pager_adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * @author by 黄梦 on 2019/2/28.
 */
class FragmentAdapter(fm: FragmentManager, private val data: List<Fragment>) : FragmentPagerAdapter(fm) {


    override fun getItem(i: Int): Fragment {
        return data[i]
    }

    override fun getCount(): Int {
        return data.size
    }

//    override fun getPageTitle(position: Int): CharSequence? {
//        if(count ==title.size)
//        {
//            return title[position]
//        }
//        return super.getPageTitle(position)
//    }

    val title = ArrayList<String>()

}
