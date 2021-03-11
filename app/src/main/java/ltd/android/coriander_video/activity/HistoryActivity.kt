package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_history.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.fragment.history.HistoryFragment
import ltd.android.coriander_video.view_model.base.BaseViewModel

class HistoryActivity : BaseActivity<BaseViewModel>() {

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, HistoryActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private val mViewPagerTitle = arrayOf("今日", "七日", "更早")
    private val mFragmentList = ArrayList<Fragment>()

    internal inner class HistoryFragmentPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return this@HistoryActivity.mViewPagerTitle.size
        }


        override fun getItem(i: Int): Fragment {
            return this@HistoryActivity.mFragmentList[i]
        }

        override fun getPageTitle(i: Int): CharSequence {
            return this@HistoryActivity.mViewPagerTitle[i]
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_history
    }

    override fun initView() {
        super.initView()
        for (position in 0..2) {
            mFragmentList.add(HistoryFragment.newInstance(position))
        }
        viewPager.adapter = HistoryFragmentPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        his_info_back_i.setOnClickListener {
            finish()
        }

    }


}


