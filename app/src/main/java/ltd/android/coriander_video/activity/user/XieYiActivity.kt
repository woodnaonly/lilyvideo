package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.activity_xie_yi.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.view_model.base.BaseViewModel


class XieYiActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, XieYiActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_xie_yi
    }


    override fun initView() {
        super.initView()
        xieyi_web.loadUrl("file:///android_asset/xieyi.html")
        xieyi_web.settings.javaScriptEnabled = true
        xieyi_web.settings.blockNetworkImage = false
        xieyi_back.setOnClickListener {
            finish()
        }

    }


}


