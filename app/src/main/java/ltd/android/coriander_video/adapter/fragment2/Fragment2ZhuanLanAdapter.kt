package ltd.android.coriander_video.adapter.fragment2

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.AVClassifyActivity
import ltd.android.coriander_video.activity.ChannelActivity
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2HotNavEntity
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2HotStarEntity
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2MustSeeNavEntity
import ltd.android.coriander_video.adapter.fragment2.entity.FragmentLableEntity
import ltd.android.coriander_video.adapter.fragment2.entity.base.Fragment2EntityBase
import ltd.android.coriander_video.adapter.fragment2.zhuanlan.Fragment2HotNavAdapter
import ltd.android.coriander_video.adapter.fragment2.zhuanlan.Fragment2HotStarAdapter
import ltd.android.coriander_video.adapter.fragment2.zhuanlan.Fragment2MustSeeNavAdapter
import ltd.android.coriander_video.fragment.Fragment2
import ltd.android.coriander_video.utils.glide.GlideUtils

/**
 * @author by 黄梦 on 2018/3/1.
 */

class Fragment2ZhuanLanAdapter : BaseMultiItemQuickAdapter<Fragment2EntityBase, BaseViewHolder> {
    val TAG = Fragment2.javaClass.simpleName

    constructor(data: List<Fragment2EntityBase>) : super(data)

    init {
        //2018必看专题
        addItemType(Fragment2EntityBase.must_see_nav, R.layout.fragment2_zhuanlan_recyclerview_item_must_see_nav)
        //热门专题
        addItemType(Fragment2EntityBase.hot_nav, R.layout.fragment2_zhuanlan_recyclerview_item_hot_nav)
        //人气明星
        addItemType(Fragment2EntityBase.hot_star, R.layout.fragment2_zhuanlan_recyclerview_item_hot_star)
        addItemType(Fragment2EntityBase.label, R.layout.fragment2_zhuanlan_recyclerview_item_lable)
    }


    override fun convert(helper: BaseViewHolder, item: Fragment2EntityBase) {
        when (item.itemType) {

            Fragment2EntityBase.must_see_nav -> {
                val entity = item as Fragment2MustSeeNavEntity
                val data = entity.data
                helper.setText(R.id.content_name, data.modName)
                val recyclerView = helper.getView<androidx.recyclerview.widget.RecyclerView>(R.id.mRecyclerView)
                val linearLayoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        recyclerView.context,
                        androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                        false
                    )
                recyclerView.layoutManager = linearLayoutManager as androidx.recyclerview.widget.RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment2MustSeeNavAdapter(data.subclass)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
            }

            Fragment2EntityBase.hot_nav -> {
                val entity = item as Fragment2HotNavEntity
                val data = entity.data
                helper.setText(R.id.content_name, data.modName)

                val recyclerView = helper.getView<androidx.recyclerview.widget.RecyclerView>(R.id.mRecyclerView)
                val linearLayoutManager =
                    androidx.recyclerview.widget.GridLayoutManager(
                        recyclerView.context,
                        4
                    )
                recyclerView.layoutManager = linearLayoutManager as androidx.recyclerview.widget.RecyclerView.LayoutManager?

                recyclerView.adapter =
                    Fragment2HotNavAdapter(data.subclass)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
                val more_type = helper.getView<View>(R.id.more_type)
                more_type.setOnClickListener {
                    ChannelActivity.start(it.context, data.subclass)
                }
            }

            Fragment2EntityBase.hot_star -> {
                val entity = item as Fragment2HotStarEntity
                val data = entity.data
                val ImageView = helper.getView<ImageView>(R.id.iv_head)
                GlideUtils.loadImg(ImageView, data.photoUrl)

                helper.setText(R.id.tv_name, data.nameCn)
                helper.setText(R.id.tv_description, data.briefIntroduction)
                helper.setText(R.id.iv_num, "${data.videosCount}部电影")
                val recyclerView = helper.getView<androidx.recyclerview.widget.RecyclerView>(R.id.mRecyclerView)
                val linearLayoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(
                        recyclerView.context,
                        androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
                        false
                    )
                recyclerView.layoutManager = linearLayoutManager as androidx.recyclerview.widget.RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment2HotStarAdapter(data.movies)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false

            }
            Fragment2EntityBase.label -> {
                val entity = item as FragmentLableEntity
                val data = entity.data
                helper.setText(R.id.content_name, data)
                val more_type = helper.getView<View>(R.id.more_type)
                more_type.setOnClickListener {
                    when (data) {
                        "人气女优" -> {
                            AVClassifyActivity.start(it.context, 0, 0)
                        }
                    }
                }
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
