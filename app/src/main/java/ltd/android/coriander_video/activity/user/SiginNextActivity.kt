package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_sigup_two.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.utils.ResourcesUtils
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 注册 下一步ACT
 */
class SiginNextActivity : BaseActivity<BaseViewModel>() {

    private var mTextViewTimeCountUtils = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            tvCodeCount.visibility = View.GONE
            btnGetCode.visibility = View.VISIBLE
        }

        override fun onTick(millisUntilFinished: Long) {
            tvCodeCount.visibility = View.VISIBLE
            btnGetCode.visibility = View.GONE
            tvCodeCount.text = "${(millisUntilFinished / 1000)}s"

        }

    }


    companion object {
        val phoneKey = "key_phone"

        fun start(context: Context?, phone: String) {
            val intent = Intent(context, SiginNextActivity::class.java)
            intent.putExtra(phoneKey, phone)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mTextViewTimeCountUtils.cancel()
    }

    override fun layoutId(): Int {
        return R.layout.activity_sigup_two
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

        val phone = intent.getStringExtra(phoneKey)
        tvPhone.text = "86 ${phone}"

        etPhone.setText(phone)

        back.setOnClickListener {
            finish()
        }

        btnGetCode.setOnClickListener {
            //todo 请求获取验证码接口
            mTextViewTimeCountUtils.start()
        }

        tvTitleRight.setOnClickListener {
            LoginActivity.start(this@SiginNextActivity)
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


