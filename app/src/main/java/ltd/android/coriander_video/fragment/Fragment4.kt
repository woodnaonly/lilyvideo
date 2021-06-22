package ltd.android.coriander_video.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import io.objectbox.Box
import kotlinx.android.synthetic.main.fragment4.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.FavActivity
import ltd.android.coriander_video.activity.HistoryActivity
import ltd.android.coriander_video.activity.user.*
import ltd.android.coriander_video.adapter.fragment4.Fragment4FavAdapter
import ltd.android.coriander_video.adapter.fragment4.Fragment4HistoryAdapter
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.dto.UserDTO
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.FavAPi
import ltd.android.coriander_video.utils.IntentUtils
import ltd.android.coriander_video.utils.ObjectBox
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class Fragment4 : BaseFragment<BaseViewModel>() {

    val userPrefsHelper = UserPrefsHelper.getInstance()
    val userDTO = userPrefsHelper.userInfo!!

    override fun layoutId(): Int {
        return R.layout.fragment4;
    }

    companion object {
        fun newInstance(): Fragment4 {
            val fragment = Fragment4()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun isApplyEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        initView()
    }


    override fun initView() {
        super.initView()



        if (userDTO.isVisit) {
            initNoLogin()
        } else {
            initLogin()
        }
        initCommon()
        bindUserInfo(userDTO)

    }

    private fun bindUserInfo(userInfo: UserDTO) {
        tv_browse_count.text = "${userInfo.dailyViewNum - userInfo.usedViewNum}/${userInfo.dailyViewNum}"

        iv_no_head.visibility = View.VISIBLE

        GlideUtils.loadImg(iv_head, userInfo.header)
//        tv_modify_phone.text = userInfo.phone
        tv_nick.text = userInfo.getName()



        if (userInfo.isVisit) {
            tv_login.text = "登录注册"
        } else {
            tv_login.text = userInfo.getLevelExplain(userInfo.level)
        }
    }

    private fun initNoLogin() {
        tv_exchange.setOnClickListener {
            LoginActivity.start(activity)
        }

        tv_login.setOnClickListener {
            LoginActivity.start(activity)
        }


    }

    private fun initCommon() {
        val loadAnimation = AnimationUtils.loadAnimation(activity, R.anim.my_promote)
        loadAnimation.interpolator = LinearInterpolator() as Interpolator?
        this.iv_promote.startAnimation(loadAnimation)

        rl_promote.setOnClickListener {
            PromotionActivity.start(activity)
        }
        iv_promote.setOnClickListener {
            PromotionActivity.start(activity)
        }

        ll_feedback.setOnClickListener {
            FeedbackActivity.start(activity)
        }

        right_iv.setOnClickListener {
            SettingActivity.start(activity)
        }
        ll_potato.setOnClickListener {
            IntentUtils.gotoUrl(it.context, userDTO.group.content)
        }

        ll_notice.setOnClickListener {
            UserNotificationActivity.start(it.context)
        }

    }


    private fun initLogin() {
        /**
         * 激活码兑换
         */
        tv_exchange.setOnClickListener {
            ExchangeActivity.start(activity)
        }

        tv_login.setOnClickListener {
            PromotionActivity.start(activity)
        }
        initHistory()
        initFav()
    }

    private fun initFav() {
        launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO)
            {
                val map = mapOf("page" to Int.MAX_VALUE)
                FavAPi.instance.getListAsync(map).await()
            }
            if (response.success) {
                val data = response.data
                tv_my_favor_num.text = "目前已有喜欢${data.size}部"
                recyclerView_favor.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recyclerView_favor.setHasFixedSize(true)
                recyclerView_favor.adapter =
                    Fragment4FavAdapter(data)
                rl_my_favor.setOnClickListener {
                    FavActivity.start(activity)
                }
            }
        }
//        tv_my_favor_num.text

//        目前已有喜欢%1$d部
    }

    private fun initHistory() {

        rl_history.setOnClickListener {
            HistoryActivity.start(it.context)
        }

        val mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor(MovieDTO::class.java)
        val list = mBox.all.filterIndexed { index, movieDTO ->
            index < 8

        }
        val count = mBox.count()
        tv_history_num.text = "目前历史观看过${count}部"
        recyclerView_history.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_history.setHasFixedSize(true)
        recyclerView_history.adapter =
            Fragment4HistoryAdapter(list)
    }
}