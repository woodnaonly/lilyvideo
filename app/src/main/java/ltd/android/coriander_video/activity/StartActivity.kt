package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.BuildConfig
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.activity.user.LoginActivity
import ltd.android.coriander_video.entity.Ad
import ltd.android.coriander_video.net.http.api.AdAPi
import ltd.android.coriander_video.net.http.api.AuthAPi
import ltd.android.coriander_video.net.http.apiImp.MemberImp
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import ltd.android.coriander_video.utils.SignUtils
import ltd.android.coriander_video.utils.UUIDS
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.File

class StartActivity : BaseActivity<BaseViewModel>() {

    private var mTextViewTimeCountUtils = object : CountDownTimer(5000, 1000) {
        override fun onFinish() {
            tv_count_down.visibility = View.GONE
            jump.visibility = View.VISIBLE
        }

        override fun onTick(millisUntilFinished: Long) {
            tv_count_down.text = (millisUntilFinished / 1000).toString()
        }

    }

    override fun layoutId(): Int {
        return R.layout.activity_start
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()

        val dir = getDir("cjq", Context.MODE_PRIVATE)

        val cacheDir = getCacheDir()
        val tempFile = File(
            cacheDir,
            "test.jpg"
        )
        Log.d("=====1", dir.absolutePath)



        launch {
            val userPrefsHelper = UserPrefsHelper.getInstance()
            if (userPrefsHelper.isHasToken) {
                val visitorResponse = withContext(Dispatchers.IO)
                {
                    AuthAPi.instance.refreshTokenAsync().await()
                }
            } else {
                try {
                    val visitorResponse = withContext(Dispatchers.IO)
                    {
                        val uuid = UUIDS.getUUID()
                        val sign = SignUtils.Sign(uuid)
                        val map = mapOf(
                            "key" to uuid,
                            "sign" to sign
                        )
                        AuthAPi.instance.getVisitorAsync(map).await()
                    }
                    if (visitorResponse.success) {
                        userPrefsHelper.token = visitorResponse.data
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
//            MemberImp.getUserInfo { }

            try {
                val adResponse = withContext(Dispatchers.IO)
                {
                    val map = mapOf(
                        "location" to "0"
                    )
                    var baseResponse: BaseResponse<List<Ad>>? = null
                    try {
                        baseResponse = AdAPi.instance.getAdAsync(map).await()
                    } catch (ex: Exception) {
                        Log.d("=====1", ex.message)
                    }
                    baseResponse
                }
                if (adResponse?.success!!) {
                    val list = adResponse.data
                    if (list.size > 0) {
                        GlideUtils.loadImg(start_img, list[0].thumbnail)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }


            ll_jump.visibility = View.VISIBLE

            jump.setOnClickListener {
                gotoMainActivity()
            }

            mTextViewTimeCountUtils.start()


            if (BuildConfig.DEBUG) {
//                gotoMainActivity()
                LoginActivity.start(this@StartActivity)

            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        mTextViewTimeCountUtils.cancel()
    }

    private fun gotoMainActivity() {
        startActivity(Intent(this@StartActivity, MainActivity::class.java))
        finish()
    }

}


