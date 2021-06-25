package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_sigup.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.utils.ResourcesUtils
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 注册ACT
 */
class SiginActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, SiginActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.activity_sigup
    }

    override fun isApplyEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        finish()
    }

    override fun initView() {
        super.initView()
        back.setOnClickListener {
            finish()
        }

        tvTitleRight.setOnClickListener {
            LoginActivity.start(this@SiginActivity)
            finish()
        }

        btnNext.setOnClickListener {
            val phone = etPhone.text.toString()
            SiginNextActivity.start(this, phone)
            finish()
        }


    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        }
    }

    private fun loginPhone(isPhone: Boolean) {
        val colorSelected = ResourcesUtils.getColor(R.color.app_brown_dark)
        val colorNoSelected = ResourcesUtils.getColor(R.color.color_E9DAC8F)
        val input_login_phone_hint = ResourcesUtils.getString(R.string.input_login_phone_hint)
        val input_login_username_hint = ResourcesUtils.getString(R.string.input_login_username_hint)


    }


}


