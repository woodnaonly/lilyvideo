package ltd.android.coriander_video.adapter

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
class FavAdapter(val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(R.layout.card_info_history, DataList) {
    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val ImageView = helper.getView<ImageView>(R.id.his_info_iamage_i)
        GlideUtils.loadImg(ImageView, item.cover)
        helper.setText(R.id.his_info_my_title_t, item.movName)
        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }

}

