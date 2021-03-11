package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_favo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.FavAdapter
import ltd.android.coriander_video.net.http.api.FavAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

class FavActivity : BaseActivity<BaseViewModel>() {

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, FavActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_favo
    }

    override fun initView() {
        super.initView()
        favor_back_i.setOnClickListener {
            finish()
        }

        launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO)
            {
                val map = mapOf("page" to Int.MAX_VALUE)
                FavAPi.instance.getListAsync(map).await()
            }
            if (response.success) {
                val data = response.data
                favor_list_r.layoutManager = LinearLayoutManager(this@FavActivity, LinearLayoutManager.VERTICAL, false)
                favor_list_r.setHasFixedSize(true)
                favor_list_r.adapter =
                    FavAdapter(data)

            }
        }

    }


}


