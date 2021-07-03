package ltd.android.coriander_video.adapter.fragment1.search

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
class LoveAdapter(internal val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(
        R.layout.fragment1_search_love,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val mCover = helper.getView<ImageView>(R.id.mCover)

        GlideUtils.loadImg(mCover, item.cover)

        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }


}
