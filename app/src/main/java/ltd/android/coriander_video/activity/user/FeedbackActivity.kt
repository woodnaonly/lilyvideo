package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_feedback.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.fragment.user.feed_back.FeedBackFragment
import ltd.android.coriander_video.fragment.user.feed_back.QuestionFragment
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*


class FeedbackActivity : BaseActivity<BaseViewModel>() {

    private val mFragmentList = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, FeedbackActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_feedback
    }


    override fun initView() {
        super.initView()
        mFragmentList.add(QuestionFragment())
        mFragmentList.add(FeedBackFragment())
//        mFragmentList.add(QuestionFragment())
        this.mTitleList.add("常见问题")
        this.mTitleList.add("我要反馈")
//        this.mTitleList.add("我的反馈")

        id_indication.setTabTitle(mTitleList)
        id_indication.setViewPager(viewPager)
        id_indication.setTextColorSelected(Color.parseColor("#b4854d"));
        id_indication.setTextNoColorSelected(Color.parseColor("#646464"));

        viewPager.offscreenPageLimit = mFragmentList.size
        this.viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }

        his_info_back_i.setOnClickListener {
            finish()
        }

    }


}


