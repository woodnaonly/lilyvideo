package ltd.android.coriander_video.adapter.fragment2.label_select

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.entity.Tag

/**
 * @author by 黄梦 on 2018/4/24.
 */
class Fragment2LabelChildAdapter(private val  DataList: List<Tag>) :

    BaseQuickAdapter<Tag, BaseViewHolder>(
        R.layout.fragment2_label_select_recyclerview_item_select_label_child,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: Tag) {
        helper.setText(R.id.tv_title, item.name)
        if (item.isSelected) {
            helper.setGone(R.id.iv_check, true)
        } else {
            helper.setGone(R.id.iv_check, false)
        }
//        helper.itemView.setOnClickListener {
//            if (selectedCount < 20) {
//                selectedCount++
//                item.isSelected = !item.isSelected
//                notifyDataSetChanged()
//            } else {
//                //todo ToastUtil.m15196a("最多只能选择20个标签");
//            }
//        }

    }




}
