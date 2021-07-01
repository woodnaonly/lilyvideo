package ltd.android.coriander_video.adapter.view_pager_adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * @author by 黄梦 on 2019/2/28.
 */
class FragmentAdapter(fm: androidx.fragment.app.FragmentManager, private val data: List<androidx.fragment.app.Fragment>) : androidx.fragment.app.FragmentPagerAdapter(fm) {


    override fun getItem(i: Int): androidx.fragment.app.Fragment {
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
