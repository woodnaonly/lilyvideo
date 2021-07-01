package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pur_promotion.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.user.PurPromotionAdapter
import ltd.android.coriander_video.dto.UserDTO
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 * 我的推广
 */
class PurPromotionActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, PurPromotionActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_pur_promotion
    }


    override fun initView() {
        super.initView()
        launch {
            val baseResponse =
                withContext(Dispatchers.IO) {
                    MemberAPi.instance.getInvitesAsync()
                }.await()

            if (baseResponse.success) {
                val list = ArrayList<UserDTO>()

                baseResponse.data.forEach {
                    list.add(it)
                }

                val adapter = PurPromotionAdapter(list)

                child_promotion_r.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(this@PurPromotionActivity)
                child_promotion_r.adapter = adapter
            }

        }


    }


}


