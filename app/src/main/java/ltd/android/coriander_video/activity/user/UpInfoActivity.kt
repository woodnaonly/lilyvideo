package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_up_info.*
import kotlinx.android.synthetic.main.personal_home_header.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.view_pager_adapter.CommPagerAdapter
import ltd.android.coriander_video.entity.VideoBean
import ltd.android.coriander_video.utils.NumUtils
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.util.*

//class MainActivity : AppCompatActivity(),CoroutineScope {
class UpInfoActivity : BaseActivity<BaseViewModel>(), View.OnClickListener {

    companion object {
        val userInfoKey = "userInfo"

        fun start(context: Context?, curUserBean: VideoBean.UserBean?) {
            val intent = Intent(context, UpInfoActivity::class.java)
            intent.putExtra(userInfoKey, curUserBean)
            context?.startActivity(intent)
        }
    }

    private val fragments = ArrayList<Fragment>()
    private var pagerAdapter: CommPagerAdapter? = null
    private var curUserBean: VideoBean.UserBean? = null


    override fun layoutId(): Int {
        return R.layout.activity_up_info
    }


    override fun initView() {
        super.initView()
//解决toolbar左边距问题
        toolbar!!.setContentInsetsAbsolute(0, 0)
        setappbarlayoutPercent()
        ivReturn!!.setOnClickListener(this)
        ivHead!!.setOnClickListener(this)
        ivBg!!.setOnClickListener(this)
        llFocus!!.setOnClickListener(this)
        llFans!!.setOnClickListener(this)
        setUserInfo()

    }


    fun setUserInfo() {
        val userInfo = intent.getSerializableExtra(userInfoKey) as VideoBean.UserBean
        coordinatorLayoutBackTop()
        this.curUserBean = userInfo
//        ivBg!!.setImageResource(userInfo.head)
//        ivHead!!.setImageResource(userInfo.head)
        GlideUtils.loadImg(ivHead, userInfo.headUrl)
        tvNickname!!.text = userInfo.nickName
        tvSign!!.text = userInfo.sign
        tvTitle!!.text = userInfo.nickName
        val subCount = NumUtils.numberFilter(userInfo.subCount)
        val focusCount = NumUtils.numberFilter(userInfo.focusCount)
        val fansCount = NumUtils.numberFilter(userInfo.fansCount)

        //获赞 关注 粉丝
        tvGetLikeCount!!.text = subCount
        tvFocusCount!!.text = focusCount
        tvFansCount!!.text = fansCount

        //关注状态
        if (userInfo.isFocused) {
            tvAddfocus!!.text = "已关注"
            tvAddfocus!!.setBackgroundResource(R.drawable.shape_round_halfwhite)
        } else {
            tvAddfocus!!.text = "关注"
            tvAddfocus!!.setBackgroundResource(R.drawable.shape_round_red)
        }
        setTabLayout()
    }

    /**
     * 自动回顶部
     */
    private fun coordinatorLayoutBackTop() {
        val behavior = (appbarlayout!!.layoutParams as CoordinatorLayout.LayoutParams).behavior
        if (behavior is AppBarLayout.Behavior) {
            val appbarlayoutBehavior = behavior
            val topAndBottomOffset = appbarlayoutBehavior.topAndBottomOffset
            if (topAndBottomOffset != 0) {
                appbarlayoutBehavior.topAndBottomOffset = 0
            }
        }
    }

    /**
     * 过渡动画跳转页面
     *
     * @param view
     */
    fun transitionAnim(view: View?, res: Int) {
//        val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this!!, view!!, getString(R.string.trans))
//        val intent = Intent(this, ShowImageActivity::class.java)
//        intent.putExtra("res", res)
//        ActivityCompat.startActivity(activity!!, intent, compat.toBundle())
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivReturn -> {
                finish()
//                RxBus.getDefault().post(MainPageChangeEvent(0))
            }
            R.id.ivHead -> {
//                transitionAnim(ivHead, curUserBean!!.head)
            }
            R.id.ivBg -> {
            }
            R.id.llFocus -> {
//                startActivity(Intent(this, FocusActivity::class.java))

            }
            R.id.llFans -> {

//                startActivity(Intent(activity, FocusActivity::class.java))
            }
        }
    }

    /**
     * 根据滚动比例渐变view
     */
    private fun setappbarlayoutPercent() {
        appbarlayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appbarlayout, verticalOffset ->
            val percent = Math.abs(verticalOffset * 1.0f) / appbarlayout.totalScrollRange //滑动比例
            if (percent > 0.8) {
                tvTitle!!.visibility = View.VISIBLE
                tvFocus!!.visibility = View.VISIBLE
                val alpha = 1 - (1 - percent) * 5 //渐变变换
                tvTitle!!.alpha = alpha
                tvFocus!!.alpha = alpha
            } else {
                tvTitle!!.visibility = View.GONE
                tvFocus!!.visibility = View.GONE
            }
        })
    }

    private fun setTabLayout() {
        val titles = arrayOf(
            "作品 " + curUserBean!!.workCount,
            "动态 " + curUserBean!!.dynamicCount,
            "喜欢 " + curUserBean!!.likeCount
        )
        fragments.clear()
        for (i in titles.indices) {
//            fragments.add(WorkFragment())
            tabLayout!!.addTab(tabLayout!!.newTab().setText(titles[i]))
        }
        pagerAdapter = CommPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager!!.adapter = pagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
    }

}


