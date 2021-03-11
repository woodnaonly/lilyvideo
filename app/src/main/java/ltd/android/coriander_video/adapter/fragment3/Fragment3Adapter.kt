package ltd.android.coriander_video.adapter.fragment3

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.fragment3.entity.Fragment3VideoEntity
import ltd.android.coriander_video.adapter.fragment3.entity.base.Fragment3EntityBase
import ltd.android.coriander_video.fragment.Fragment3
import ltd.android.coriander_video.net.http.api.FavAPi
import ltd.android.coriander_video.utils.ClipboardManagerUtils
import ltd.android.coriander_video.utils.ToastUtil
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.video.SampleCoverVideo
import ltd.android.coriander_video.video.callback.LocalGSYCallBack

/**
 * @author by 梁馨 on 2018/3/1.
 */

class Fragment3Adapter : BaseMultiItemQuickAdapter<Fragment3EntityBase, BaseViewHolder> {
    val TAG = Fragment3.javaClass.simpleName

    constructor(data: List<Fragment3EntityBase>) : super(data)

    init {
        addItemType(Fragment3EntityBase.item_video, R.layout.fragment3_recyclerview_item_video)
    }


    override fun convert(helper: BaseViewHolder, item: Fragment3EntityBase) {
        when (item.itemType) {
            Fragment3EntityBase.item_video -> {
                val videoEntity = item as Fragment3VideoEntity
                val data = videoEntity.data

                val mSampleCoverVideo = helper.getView<SampleCoverVideo>(R.id.mSampleCoverVideo)
                val imageView = ImageView(helper.itemView.context)
                imageView.setImageResource(R.mipmap.ic_launcher)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                GlideUtils.loadImg(imageView, data.cover)
                helper.setText(R.id.play_times, data.playCountFormat)

                val share_image = helper.getView<View>(R.id.share_image)
                share_image.setOnClickListener {
                    ClipboardManagerUtils.CopyToClipboard(it.context, "复制测试")
                    ToastUtil.getInstance().showToast("已复制内容到剪贴板")
                }

                val favor_image = helper.getView<ImageView>(R.id.favor_image)


                favor_image.setOnClickListener {
                    GlobalScope.launch {
                        val response = withContext(Dispatchers.IO)
                        {
                            val mapOf = mapOf("id" to data.id)
                            FavAPi.instance.addAsync(mapOf)
                        }.await()

                    }
                    favor_image.setImageResource(R.drawable.favor_press)
                }
                if (data.isFav()) {
                    favor_image.setImageResource(R.drawable.favor_press)
                } else {
                    favor_image.setImageResource(R.drawable.favor_nopress)
                }

                val mGSYVideoOptionBuilder = GSYVideoOptionBuilder()
                mGSYVideoOptionBuilder
                    .setIsTouchWiget(false)
                    .setThumbImageView(imageView)
                    .setUrl(data.file)
                    .setVideoTitle(data.movName)
                    .setCacheWithPlay(false)
                    .setRotateViewAuto(true)
                    .setLockLand(true)
                    .setPlayTag(TAG)
                    .setShowFullAnimation(true)
                    .setNeedLockFull(true)
                    .setPlayPosition(0)
                    .setVideoAllCallBack(LocalGSYCallBack(mSampleCoverVideo, data)).build(mSampleCoverVideo)

                //增加title
                mSampleCoverVideo.getTitleTextView().setVisibility(View.GONE)

                //设置返回键
                mSampleCoverVideo.getBackButton().setVisibility(View.GONE)

                //设置全屏按键功能
                mSampleCoverVideo.getFullscreenButton()
                    .setOnClickListener(View.OnClickListener {
                        resolveFullBtn(it.context, mSampleCoverVideo)
                    })

            }
            Fragment3EntityBase.item_ad -> {

            }
        }
    }

    /**
     * 全屏幕按键处理
     */
    private fun resolveFullBtn(context: Context, standardGSYVideoPlayer: StandardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true)
    }
}
