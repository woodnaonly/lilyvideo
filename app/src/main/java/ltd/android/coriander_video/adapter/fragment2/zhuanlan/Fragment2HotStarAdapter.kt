package ltd.android.coriander_video.adapter.fragment2.zhuanlan

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.VideoPlayActivity
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 黄梦 on 2018/4/24.
 */
class Fragment2HotStarAdapter(internal val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(
        R.layout.fragment2_zhuanlan_recyclerview_item_hot_star_item_move,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val ImageView = helper.getView<ImageView>(R.id.mov_img)
        GlideUtils.loadImg(ImageView, item.cover)
        helper.setText(R.id.tv_score, item.movScore.toString())
        helper.setText(R.id.mov_name, item.movName)
        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }


}
