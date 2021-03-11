package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_channel_more.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.channel_activity.ChannelAdapter
import ltd.android.coriander_video.dto.ColumnNavDTO
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.Serializable

class ChannelActivity : BaseActivity<BaseViewModel>() {


    companion object {
        private const val dataName = "data"

        fun start(context: Context?, data: MutableList<ColumnNavDTO.Nav>) {
            val intent = Intent(context, ChannelActivity::class.java)
            intent.putExtra(dataName, data as Serializable)
            context?.startActivity(intent)
        }
    }

    val data = ArrayList<ArrayList<ColumnNavDTO.Nav>>()
    val mAdapter = ChannelAdapter(data)
    private val mLinearLayoutManager = LinearLayoutManager(this)
    override fun layoutId(): Int {
        return R.layout.activity_channel_more
    }


    override fun initView() {
        super.initView()
        var tList = ArrayList<ColumnNavDTO.Nav>()
        val intent = intent
        val list = intent.getSerializableExtra(dataName) as List<ColumnNavDTO.Nav>
        list.forEachIndexed { index, nav ->

            if (index % 8 == 0) {
                tList = ArrayList<ColumnNavDTO.Nav>()
                data.add(tList)
            }
            tList.add(nav)
        }

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager


    }


    override fun onDestroy() {
        super.onDestroy()
    }


}


