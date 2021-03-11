package ltd.android.coriander_video.adapter.fragment1

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.ClassifyActivity
import ltd.android.coriander_video.entity.MovieClass
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 梁馨 on 2018/4/24.
 */
class Fragment1MovieClassAdapter(val DataList: List<MovieClass>) :
    BaseQuickAdapter<MovieClass, BaseViewHolder>(
        R.layout.fragment1_recyclerview_item_movie_class_item_grid,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieClass) {
        val ImageView = helper.getView<ImageView>(R.id.iv_head)
        GlideUtils.loadImg(ImageView, item.clsIcon)
        helper.setText(R.id.tv_name, item.clsName)
        helper.itemView.setOnClickListener {
            if (item.clsName == "全部") {
                ClassifyActivity.start(it.context, 0, 0)
            } else {
                ClassifyActivity.start(it.context, 0, helper.adapterPosition + 1)
            }
        }


    }

    override fun getItem(position: Int): MovieClass? {
        if (position == 7) {
            DataList[position].clsName = "全部"
        }


        return super.getItem(position)
    }

    override fun getItemCount(): Int {
        if (DataList.size >= 8) {
            return 8
        }

        return super.getItemCount()
    }
}
