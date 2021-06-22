package ltd.android.coriander_video.adapter.channel_activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.dto.ColumnNavDTO
import java.util.*

/**
 * @author by 黄梦 on 2018/4/24.
 */
class ChannelAdapter(val DataList: ArrayList<ArrayList<ColumnNavDTO.Nav>>) :
    BaseQuickAdapter<ArrayList<ColumnNavDTO.Nav>, BaseViewHolder>(
        R.layout.activity_channel_more_recyclerview,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: ArrayList<ColumnNavDTO.Nav>) {
        val recyclerView = helper.getView<RecyclerView>(R.id.mRecyclerView)
        val gridLayoutManager =
            GridLayoutManager(recyclerView.context, 4)
        recyclerView.layoutManager = gridLayoutManager

        recyclerView.adapter =
            ChannelItemAdapter(item)
        recyclerView.setHasFixedSize(true)
        recyclerView.isFocusable = false

    }

    override fun getItemCount(): Int {
        if (DataList.size >= 8) {
            return 8
        }

        return super.getItemCount()
    }
}
