package ltd.android.coriander_video.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment2.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.fragment.fragment2.LabelSelectFragment
import ltd.android.coriander_video.fragment.fragment2.ZhuanlanFragment
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class Fragment2 : BaseFragment<BaseViewModel>() {
    private val mFragmentList = ArrayList<androidx.fragment.app.Fragment>()
    private val mTitleList = ArrayList<String>()

    override fun layoutId(): Int {
        return R.layout.fragment2
    }

    companion object {
        fun newInstance(): Fragment2 {
            val fragment = Fragment2()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        this.mTitleList.add("专栏推荐")
        this.mTitleList.add("标签筛选")
        this.mFragmentList.add(ZhuanlanFragment())
        this.mFragmentList.add(LabelSelectFragment())
        mViewPagerIndicator_Fix.setTabTitle(mTitleList)
        mViewPagerIndicator_Fix.setViewPager(mIndexViewPager)
        mIndexViewPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
            }

        })
        this.mIndexViewPager.setScanScroll(true)
        this.mIndexViewPager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(p0: Int): androidx.fragment.app.Fragment {
                return mFragmentList.get(p0)
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }
    }
}