package ltd.android.coriander_video.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.fragment1.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.view_pager_adapter.CommPagerAdapter
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.fragment.fragment1.HomeChildrenFragment1
import ltd.android.coriander_video.view_model.HomeViewModel
import java.util.*

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class Fragment1 : BaseFragment<HomeViewModel>() {

    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java


    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null


    override fun layoutId(): Int {
        return R.layout.fragment1;
    }

    companion object {
        fun newInstance(): Fragment1 {
            val fragment = Fragment1()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        setFragments()
    }

    private fun setFragments() {
//        currentLocationFragment = CurrentLocationFragment()
//        recommendFragment = RecommendFragment()
        fragments.add(HomeChildrenFragment1())
        fragments.add(HomeChildrenFragment1())
        fragments.add(HomeChildrenFragment1())
        fragments.add(HomeChildrenFragment1())
//        fragments.add(recommendFragment!!)
//        mXTabLayout!!.addTab(mXTabLayout!!.newTab().setText("同城"))
//        mXTabLayout!!.addTab(mXTabLayout!!.newTab().setText("关注"))
//        mXTabLayout!!.addTab(mXTabLayout!!.newTab().setText("热门"))
//        mXTabLayout!!.addTab(mXTabLayout!!.newTab().setText("推荐"))
        pagerAdapter =
            CommPagerAdapter(childFragmentManager, fragments, arrayOf("同城", "推荐", "关注", "推荐"))
        viewPager!!.adapter = pagerAdapter
        mXTabLayout!!.setupWithViewPager(viewPager)
//        mXTabLayout!!.getTabAt(1)!!.select()
        viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    override fun startObserve() {
        super.startObserve()
//

    }
}