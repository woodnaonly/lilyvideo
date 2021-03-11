package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_exchange.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.net.http.apiImp.MemberImp
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 * 激活码兑换
 */
class ExchangeActivity : BaseActivity<BaseViewModel>() {

    val userPrefsHelper = UserPrefsHelper.getInstance()
    val userDTO = userPrefsHelper.userInfo!!

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, ExchangeActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_exchange
    }


    override fun initView() {
        super.initView()

        exchange_back.setOnClickListener {
            finish()
        }
        GlideUtils.loadImg(user_icon, userDTO.header)
        user_name.text = userDTO.getName()

        activie_code_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = activie_code_edit.text.toString()
                if (text.isNotEmpty()) {
                    exchange_button.isClickable = true
                    exchange_button.setBackgroundResource(R.drawable.shape_text_border)
                } else {
                    exchange_button.isClickable = false
                    exchange_button.setBackgroundResource(R.drawable.shape_normal_button_border)
                }
            }
        })

        exchange_button.setOnClickListener {

            launch {
                val response = withContext(Dispatchers.IO)
                {
                    val map = mapOf(
                        "vipCode" to activie_code_edit.text.toString()
                    )
                    MemberAPi.instance.useVipCodeAsync(map).await()
                }

                if (response.success) {
                    toastUtil.showToast("兑换成功")
                    finish()
                    MemberImp.getUserInfo { }
                } else {
                    toastUtil.showToast(response.msg)
                }
            }
        }
    }


}


