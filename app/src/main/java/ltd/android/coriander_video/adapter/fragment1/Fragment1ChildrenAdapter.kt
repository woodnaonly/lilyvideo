package ltd.android.coriander_video.adapter.fragment1

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
class Fragment1ChildrenAdapter(val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(
        R.layout.item_grid_view,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val ImageView = helper.getView<ImageView>(R.id.list_button)
        GlideUtils.loadImg(ImageView, item.cover)
        helper.setText(R.id.list_title, item.movName)
        helper.setText(R.id.tv_score, item.movScore.toString())
        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }
}
