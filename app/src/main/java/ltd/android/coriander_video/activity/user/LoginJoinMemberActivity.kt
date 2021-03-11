package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_registration_two.*
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.app.App
import ltd.android.coriander_video.net.http.api.AuthAPi
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.utils.SignUtils
import ltd.android.coriander_video.utils.UUIDS
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel


class LoginJoinMemberActivity : BaseActivity<BaseViewModel>() {

    private var mTextViewTimeCountUtils = object : CountDownTimer(60000, 1000) {
        override fun onFinish() {
            Get_Verification_number.visibility = View.GONE
            jump.visibility = View.VISIBLE
        }

        override fun onTick(millisUntilFinished: Long) {
            Get_Verification_number.text = "${(millisUntilFinished / 1000)}s"

        }

    }


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, LoginJoinMemberActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return R.layout.activity_registration_two
    }


    override fun initView() {
        super.initView()

        back.setOnClickListener {
            finish()
        }


        tv_agreement.setOnClickListener {
            XieYiActivity.start(this)
        }

        text_regist_phone_login.setOnClickListener {
            LoginActivity.start(this)
            finish()
        }
        //返回上一步
        linelayout_back.setOnClickListener {
            linelayout_regist_phone_one.visibility = View.VISIBLE
            linelayout_regist_phone_two.visibility = View.GONE
            mTextViewTimeCountUtils.cancel()
        }


        ShowPassword_Join.setOnClickListener {
            val transformationMethod = register_passwrod_p.transformationMethod
            if (transformationMethod is HideReturnsTransformationMethod) {
                //显示为明文
                register_passwrod_p.transformationMethod = PasswordTransformationMethod.getInstance()
            } else {
                //显示为密文
                register_passwrod_p.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }
        }

        register_phone_p.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val mobile = register_phone_p.text.toString()
                if (mobile.length == 11) {
                    register_phone_next.isClickable = true
                    register_phone_next.setBackgroundResource(R.drawable.shape_text_border)
                } else {
                    register_phone_next.isClickable = false
                    register_phone_next.setBackgroundResource(R.drawable.shape_normal_button_border)
                }
            }
        })

        register_phone_next.setOnClickListener {
            launch {
                val response = withContext(Dispatchers.IO)
                {
                    val uuid = UUIDS.getUUID()
                    val mobile = register_phone_p.text.toString()
                    val sign = SignUtils.Sign(uuid + mobile)
                    val map = mapOf(
                        "key" to uuid,
                        "mobile" to mobile,
                        "sign" to sign
                    )
                    AuthAPi.instance.regMsgAsync(map).await()
                }

                if (response.success) {
                    linelayout_regist_phone_one.visibility = View.GONE
                    linelayout_regist_phone_two.visibility = View.VISIBLE
                    text_regist_number_title.text = register_phone_p.text.toString()
                    Enter_Verification_number.setText(response.data)
                    mTextViewTimeCountUtils.start()
                }
            }
        }

        Enter_Verification_number.addTextChangedListener(registerCommitCheck())
        register_passwrod_p.addTextChangedListener(registerCommitCheck())


        register_phone_commit_b.setOnClickListener {
            launch {
                val response = withContext(Dispatchers.IO)
                {
                    val uuid = UUIDS.getUUID()
                    val mobile = register_phone_p.text.toString()
                    val password = register_passwrod_p.text.toString()
                    val code = Enter_Verification_number.text.toString()
                    val inviteCode = register_invite_code_u.text.toString()
                    val map = mapOf(
                        "key" to uuid,
                        "mobile" to mobile,
                        "code" to code,
                        "password" to password,
                        "inviteCode" to inviteCode
                    )
                    AuthAPi.instance.registerAsync(map).await()
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
                            UserManagerActivity.start(this@LoginJoinMemberActivity)
                            val app = App.getInstance()
                            app.stopActivity(LoginActivity::class.java)
                            finish()
                        }
                    }

                }
            }
        }

    }

    private fun registerCommitCheck(): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val verificationNumbe = Enter_Verification_number.text.toString()
                val registerPasswrod = register_passwrod_p.text.toString()
                if (verificationNumbe.length >= 4 && registerPasswrod.isNotEmpty()) {
                    register_phone_commit_b.isClickable = true;
                    register_phone_commit_b.setBackgroundResource(R.drawable.shape_text_border)
                } else {
                    register_phone_commit_b.isClickable = false;
                    register_phone_commit_b.setBackgroundResource(R.drawable.shape_normal_button_border)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTextViewTimeCountUtils.cancel()
    }
}


