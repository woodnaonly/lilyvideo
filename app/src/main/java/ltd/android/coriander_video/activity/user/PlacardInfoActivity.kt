package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.activity_placard_info.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.dto.NoticeDTO
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.Serializable


class PlacardInfoActivity : BaseActivity<BaseViewModel>() {

    companion object {
        private const val dataName = "data"
        fun start(noticeDTO: NoticeDTO, context: Context?) {
            val intent = Intent(context, PlacardInfoActivity::class.java)
            intent.putExtra(PlacardInfoActivity.dataName, noticeDTO as Serializable)
            context?.startActivity(intent)
        }
    }

    lateinit var data: NoticeDTO


    override fun layoutId(): Int {
        return R.layout.activity_placard_info
    }


    override fun initView() {
        super.initView()

        val intent = intent
        data = intent.getSerializableExtra(PlacardInfoActivity.dataName) as NoticeDTO

        placecard_info_title.text=data.title

        placard_info_web.loadDataWithBaseURL(null, data.content, "text/html", "UTF-8", null);

        placard_info_back.setOnClickListener {
            finish()
        }


    }


}


