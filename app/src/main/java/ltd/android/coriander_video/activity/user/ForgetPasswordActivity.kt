package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.view_model.base.BaseViewModel


class ForgetPasswordActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, ForgetPasswordActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_forget_password_two
    }


    override fun initView() {
        super.initView()


    }


}


