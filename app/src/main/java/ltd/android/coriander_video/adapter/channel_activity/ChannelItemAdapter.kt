package ltd.android.coriander_video.adapter.channel_activity

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.NavActivity
import ltd.android.coriander_video.dto.ColumnNavDTO
import ltd.android.coriander_video.utils.glide.GlideUtils
import java.util.*

/**
 * @author by 梁馨 on 2018/4/24.
 */
class ChannelItemAdapter(val DataList: ArrayList<ColumnNavDTO.Nav>) :
    BaseQuickAdapter<ColumnNavDTO.Nav, BaseViewHolder>(
        R.layout.activity_channel_more_recyclerview_item,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: ColumnNavDTO.Nav) {
        val ImageView = helper.getView<ImageView>(R.id.iv_head)
        GlideUtils.loadImg(ImageView, item.cover)
        helper.setText(R.id.tv_name, item.navName)
        helper.itemView.setOnClickListener {
            NavActivity.start(it.context, item)
        }
    }

    override fun getItemCount(): Int {
        if (DataList.size >= 8) {
            return 8
        }

        return super.getItemCount()
    }
}
