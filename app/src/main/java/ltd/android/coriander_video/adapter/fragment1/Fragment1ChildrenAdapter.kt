package ltd.android.coriander_video.adapter.fragment1

import com.bytedance.tiktok.view.ControllerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.entity.VideoBean
import ltd.android.coriander_video.view.LikeView

/**
 * @author by 黄梦 on 2018/4/24.
 */
class Fragment1ChildrenAdapter(val DataList: List<VideoBean>) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(
        R.layout.item_fragment1_children,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        val mControllerView = helper.getView<ControllerView>(R.id.mControllerView)
        mControllerView.setVideoData(item)

        val mLikeView = helper.getView<LikeView>(R.id.mLikeView)
        mLikeView.setOnLikeListener(object : LikeView.OnLikeListener {
            override fun onLikeListener() {
                if (!item.isLiked) {  //未点赞，会有点赞效果，否则无
                    mControllerView.like()
                }

            }
        })

    }
}
