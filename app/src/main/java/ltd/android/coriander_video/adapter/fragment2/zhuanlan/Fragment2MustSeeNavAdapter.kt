package ltd.android.coriander_video.adapter.fragment2.zhuanlan

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.NavActivity
import ltd.android.coriander_video.dto.ColumnNavDTO
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 梁馨 on 2018/4/24.
 */
class Fragment2MustSeeNavAdapter(DataList: List<ColumnNavDTO.Nav>) :
    BaseQuickAdapter<ColumnNavDTO.Nav, BaseViewHolder>(
//        R.layout.fragment2_zhuanlan_recyclerview_item_must_see_nat_item,
        R.layout.fragment2_zhuanlan_recyclerview_item_must_see_nat_item,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: ColumnNavDTO.Nav) {
        val ImageView = helper.getView<ImageView>(R.id.iv_head)
        GlideUtils.loadImg(ImageView, item.navImage)
        helper.setText(R.id.tv_title, item.navName)
        helper.setText(R.id.tv_date, "上新时间:${item.lastUpdateTime}")
        helper.setText(R.id.tv_des, item.intro)

        helper.itemView.setOnClickListener {
            NavActivity.start(it.context, item)
        }
    }

}
