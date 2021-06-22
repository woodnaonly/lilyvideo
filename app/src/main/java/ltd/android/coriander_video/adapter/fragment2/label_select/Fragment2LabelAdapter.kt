package ltd.android.coriander_video.adapter.fragment2.label_select

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.dto.TagDTO

/**
 * @author by 黄梦 on 2018/4/24.
 */
class Fragment2LabelAdapter(private val DataList: List<TagDTO>) :


    BaseQuickAdapter<TagDTO, BaseViewHolder>(
        R.layout.fragment2_label_select_recyclerview_item_select_label,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: TagDTO) {
        helper.setText(R.id.tv_title, item.pname)

        if (selectedCount == helper.adapterPosition) {
            helper.setBackgroundRes(R.id.linearLayout, R.drawable.shape_label_selected)
        } else {
            helper.setBackgroundRes(R.id.linearLayout, R.color.white)
        }
        //右下打勾
        if (item.isSelected) {
            helper.setGone(R.id.iv_check, true)
        } else {
            helper.setGone(R.id.iv_check, false)
        }

        if (helper.getAdapterPosition() === DataList.size - 1) {
            helper.setGone(R.id.iv_icon, true)
        } else {
            helper.setGone(R.id.iv_icon, false)
        }

    }

    var selectedCount = 0


}
