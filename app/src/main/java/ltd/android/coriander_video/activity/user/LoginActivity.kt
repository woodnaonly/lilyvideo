package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_login_two.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.net.http.api.AuthAPi
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.utils.ResourcesUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class LoginActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.activity_login_two
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

        //跳转到注册
        JoinMember.setOnClickListener {
            LoginJoinMemberActivity.start(this)
        }

        //跳转到找回密码
        GetPassword.setOnClickListener {
            ForgetPasswordActivity.start(this)
//            XieYiActivity.start(this)
        }

        login_phone.setOnClickListener {
            loginPhone(true)
        }
        login_username.setOnClickListener {
            loginPhone(false)
        }


        ShowPassword.setOnClickListener {
            val transformationMethod = EnterPassword.transformationMethod
            if (transformationMethod is HideReturnsTransformationMethod) {
                //显示为明文
                EnterPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                //显示为密文
                EnterPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }

        EnterAccount.addTextChangedListener(textWatcher())
        EnterPassword.addTextChangedListener(textWatcher())


        Enter_Login.setOnClickListener {
            launch {
                val response = withContext(Dispatchers.IO)
                {
                    val mobile = EnterAccount.text.toString()
                    val password = EnterPassword.text.toString()
                    val map = mapOf(
                        "mobile" to mobile,
                        "password" to password
                    )
                    AuthAPi.instance.signinAsync(map).await()
                }

                if (response.success) {
                    val token = response.data
                    val userPrefsHelper = UserPrefsHelper.getInstance()
                    userPrefsHelper.clearToken();
                    userPrefsHelper.token = token
                    /**
                     * 获取用户信息
                     */
                    runBlocking {
                        val userInfoResponse = withContext(Dispatchers.IO)
                        {
                            MemberAPi.instance.getUserInfoAsync().await()
                        }
                        if (userInfoResponse.success) {
                            userPrefsHelper.setUserInfo(userInfoResponse.data)
                            EventBus.getDefault().post(LoginEvent())
                            finish()
                        }
                    }
                } else {
                    toastUtil.showToast(response.msg)
                }
            }
        }


    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val mobile = EnterAccount.text.toString()
                val password = EnterPassword.text.toString()
                if (mobile.length == 11 && password.isNotEmpty()) {
                    Enter_Login.isClickable = true;
                    Enter_Login.setBackgroundResource(R.drawable.shape_text_border)
                } else {
                    Enter_Login.isClickable = false;
                    Enter_Login.setBackgroundResource(R.drawable.shape_normal_button_border)
                }

            }

        }
    }

    private fun loginPhone(isPhone: Boolean) {
        val colorSelected = ResourcesUtils.getColor(R.color.app_brown_dark)
        val colorNoSelected = ResourcesUtils.getColor(R.color.color_E9DAC8F)
        val input_login_phone_hint = ResourcesUtils.getString(R.string.input_login_phone_hint)
        val input_login_username_hint = ResourcesUtils.getString(R.string.input_login_username_hint)

        EnterPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        if (isPhone) {
            login_phone.setTextColor(colorSelected)
            login_username.setTextColor(colorNoSelected)

            line_choose_country.visibility = View.VISIBLE
            image_username_back.visibility = View.GONE
            image_password_back.visibility = View.GONE
            image_triangle_phone.visibility = View.VISIBLE

            image_triangle_username.visibility = View.GONE


            EnterAccount.hint = input_login_phone_hint

        } else {
            login_phone.setTextColor(colorNoSelected)
            login_username.setTextColor(colorSelected)

            line_choose_country.visibility = View.GONE
            image_username_back.visibility = View.VISIBLE
            image_password_back.visibility = View.VISIBLE
            image_triangle_phone.visibility = View.GONE

            image_triangle_username.visibility = View.VISIBLE
            EnterAccount.hint = input_login_username_hint
        }
    }


}


