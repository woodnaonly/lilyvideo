package ltd.android.coriander_video.fragment.fragment1

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.fragment1.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.view_pager_adapter.CommPagerAdapter
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.view_model.HomeViewModel
import java.util.*

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class ChildrenFragment1 : BaseFragment<HomeViewModel>() {

    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java




    override fun layoutId(): Int {
        return R.layout.fragment1_children1;
    }

    companion object {
        fun newInstance(): ChildrenFragment1 {
            val fragment = ChildrenFragment1()
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

    }


    override fun startObserve() {
        super.startObserve()
//

    }
}