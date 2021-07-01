package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_user_notification.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.fragment.user.UserNotificationFragment
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*


class UserNotificationActivity : BaseActivity<BaseViewModel>() {
    private val mFragmentList = ArrayList<androidx.fragment.app.Fragment>()
    private val mTitleList = ArrayList<String>()

    companion object {
        fun start( context: Context?) {
            val intent = Intent(context, UserNotificationActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_user_notification
    }


    override fun initView() {
        super.initView()
        mFragmentList.add(UserNotificationFragment())
        this.mTitleList.add("公告")
        id_indication.setTabTitle(mTitleList)
        id_indication.setViewPager(view_pager)

        this.view_pager.adapter = object : androidx.fragment.app.FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): androidx.fragment.app.Fragment {
                return mFragmentList.get(p0)
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }

        image_user_back.setOnClickListener {
            finish()
        }


    }


}


