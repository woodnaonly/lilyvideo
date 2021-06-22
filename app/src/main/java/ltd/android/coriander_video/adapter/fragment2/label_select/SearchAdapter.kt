package ltd.android.coriander_video.adapter.fragment2.label_select

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.VideoPlayActivity
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 黄梦 on 2018/4/24.
 */
class SearchAdapter(internal val DataList: List<MovieDTO>) :
    BaseQuickAdapter<MovieDTO, BaseViewHolder>(
        R.layout.fragment2_label_select_recyclerview_item_guess_like,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: MovieDTO) {
        val guess_pic = helper.getView<ImageView>(R.id.guess_pic)
        val mov_name = helper.getView<TextView>(R.id.mov_name)
        val tv_score = helper.getView<TextView>(R.id.tv_score)
        val mov_play_times = helper.getView<TextView>(R.id.mov_play_times)
        val mov_label = helper.getView<TagFlowLayout>(R.id.mov_label)

        mov_name.text = item.movName
        tv_score.text = item.movScore.toString()
        mov_play_times.text = item.playCountFormat
        GlideUtils.loadImg(guess_pic, item.cover)

        val tagList = ArrayList<String>()
        item.relTagName.forEach {
            tagList.add(it.name)
        }

        mov_label.adapter = object : TagAdapter<String>(tagList) {
            val layoutInflater = LayoutInflater.from(helper.itemView.context)
            override fun getView(parent: FlowLayout?, position: Int, str: String?): View {
                val textView = layoutInflater.inflate(R.layout.item_video_textview, mov_label, false) as TextView
                textView.text = str
                return textView
            }

        }

        helper.itemView.setOnClickListener {
            VideoPlayActivity.start(it.context, item)
        }
    }


}
