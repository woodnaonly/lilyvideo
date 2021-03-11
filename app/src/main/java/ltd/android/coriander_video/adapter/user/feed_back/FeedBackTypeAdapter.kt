package ltd.android.coriander_video.adapter.user.feed_back

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.dto.FeedBackTypeDTO
import ltd.android.coriander_video.utils.ResourcesUtils

/**
 * @author by 梁馨 on 2018/4/24.
 */
class FeedBackTypeAdapter(val DataList: List<FeedBackTypeDTO>) :
    BaseQuickAdapter<FeedBackTypeDTO, BaseViewHolder>(
        R.layout.item_feedback_ques,
        DataList
    ) {

    var selectedPositin = -1


    override fun convert(helper: BaseViewHolder, item: FeedBackTypeDTO) {
        val textView = helper.getView<TextView>(R.id.tv)
        if (selectedPositin == helper.layoutPosition) {
            helper.setBackgroundRes(R.id.tv, R.drawable.shape_feedback_ques_sel)
            helper.setTextColor(R.id.tv, ResourcesUtils.getColor(R.color.white))
        } else {
            helper.setBackgroundRes(R.id.tv, R.drawable.shape_feedback_ques_nor)
            helper.setTextColor(R.id.tv, ResourcesUtils.getColor(R.color.color_969696))
        }

        helper.setText(R.id.tv, item.typeName)


        helper.itemView.setOnClickListener {
            selectedPositin = helper.layoutPosition
            notifyDataSetChanged()
        }

    }

}
