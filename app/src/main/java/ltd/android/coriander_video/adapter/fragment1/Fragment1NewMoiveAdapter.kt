package ltd.android.coriander_video.adapter.fragment1

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.VideoPlayActivity
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 梁馨 on 2018/4/24.
 */
class Fragment1NewMoiveAdapter(val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(
        R.layout.item_grid_view,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val ImageView = helper.getView<ImageView>(R.id.list_button)
        GlideUtils.loadImg(ImageView, item.cover)
        helper.setText(R.id.list_title, item.movName)
        helper.setVisible(R.id.tv_score, false)
        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }

    override fun getItemCount(): Int {
        if (DataList.size >= 8) {
            return 8
        }

        return super.getItemCount()
    }
}
