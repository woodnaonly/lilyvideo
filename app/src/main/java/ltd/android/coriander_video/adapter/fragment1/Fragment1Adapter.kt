package ltd.android.coriander_video.adapter.fragment1

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.ClassifyActivity
import ltd.android.coriander_video.activity.NavActivity
import ltd.android.coriander_video.adapter.fragment1.entity.*
import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase
import ltd.android.coriander_video.entity.Ad

/**
 * @author by 黄梦 on 2018/3/1.
 */

class Fragment1Adapter : BaseMultiItemQuickAdapter<Fragment1EntityBase, BaseViewHolder> {

    constructor(data: List<Fragment1EntityBase>) : super(data)

    init {
        //轮播
        addItemType(Fragment1EntityBase.banner, R.layout.fragment1_recyclerview_item_banner)
        //分类
        addItemType(Fragment1EntityBase.movie_class, R.layout.fragment1_recyclerview_item_movie_class)
        //最新片源
        addItemType(Fragment1EntityBase.new_moive, R.layout.fragment_content_recycler_view)
        //重播热榜
        addItemType(Fragment1EntityBase.hot_moive, R.layout.fragment_content_recycler_view)
        //猜你喜欢
        addItemType(Fragment1EntityBase.guess_like, R.layout.fragment1_recyclerview_item_guess_like)
        //栏目
        addItemType(Fragment1EntityBase.home_columns, R.layout.fragment_content_recycler_view)
        //分割线
        addItemType(Fragment1EntityBase.divide_line, R.layout.view_line)
    }


    override fun convert(helper: BaseViewHolder, item: Fragment1EntityBase) {
        when (item.itemType) {
            Fragment1EntityBase.banner -> {
                val entity = item as Fragment1BannerEntity
                val data = entity.data
                val mZBannerView = helper.getView<MZBannerView<Ad>>(R.id.banner_normal)
                mZBannerView.setPages(data, object : MZHolderCreator<BannerViewHolder> {
                    override fun createViewHolder(): BannerViewHolder {
                        return BannerViewHolder()
                    }

                })
            }
            Fragment1EntityBase.movie_class -> {
                val entity = item as Fragment1MoiveClassEntity
                val data = entity.data
                val recyclerView = helper.getView<RecyclerView>(R.id.mRecyclerView)
                val linearLayoutManager =
                    GridLayoutManager(recyclerView.context, 4)
                recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment1MovieClassAdapter(data)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
            }

            Fragment1EntityBase.new_moive -> {
                val entity = item as Fragment1NewMoiveEntity
                val data = entity.data
                val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
                val linearLayoutManager =
                    GridLayoutManager(recyclerView.context, 2)
                helper.setText(R.id.content_name, "最新片源")
                val more_type = helper.getView<View>(R.id.more_type)
                more_type.setOnClickListener {
                    ClassifyActivity.start(it.context, 2, 0)
                }

                recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment1NewMoiveAdapter(data)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
            }

            Fragment1EntityBase.hot_moive -> {
                val entity = item as Fragment1HotMoiveEntity
                val data = entity.data
                val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
                val linearLayoutManager =
                    GridLayoutManager(recyclerView.context, 2)
                helper.setText(R.id.content_name, "重播热榜")
                val fragment1HotMoiveAdapter = Fragment1HotMoiveAdapter(data)
                recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
                recyclerView.adapter =
                    fragment1HotMoiveAdapter
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
                val mbtnChangeContent = helper.getView<View>(R.id.mbtnChangeContent)
                mbtnChangeContent.visibility = View.VISIBLE
                mbtnChangeContent.setOnClickListener {
                    mChangeContentListener?.onClick(fragment1HotMoiveAdapter)
                }

                val more_type = helper.getView<View>(R.id.more_type)
                more_type.setOnClickListener {
                    ClassifyActivity.start(it.context, 0, 0)
                }

            }
            Fragment1EntityBase.guess_like -> {
                val entity = item as Fragment1GuessLikeEntity
                val data = entity.data
                val recyclerView = helper.getView<RecyclerView>(R.id.guess_data_recyclerView)
                val linearLayoutManager =
                    LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment1GuessLikeAdapter(data)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false
            }

            Fragment1EntityBase.home_columns -> {
                val entity = item as Fragment1ColumnsMoiveEntity
                val data = entity.data
                val recyclerView = helper.getView<RecyclerView>(R.id.recyclerView)
                val linearLayoutManager =
                    GridLayoutManager(recyclerView.context, 3)
                helper.setText(R.id.content_name, data.name)

                recyclerView.layoutManager = linearLayoutManager as RecyclerView.LayoutManager?
                recyclerView.adapter =
                    Fragment1ColumnAdapter(data)
                recyclerView.setHasFixedSize(true)
                recyclerView.isFocusable = false

                val more_type = helper.getView<View>(R.id.more_type)
                more_type.setOnClickListener {
                    val nav = data.transformNav()
                    NavActivity.start(it.context, nav)
                }
            }
        }
    }


    var mChangeContentListener: ChangeContentListener? = null


    interface ChangeContentListener {
        fun onClick(fragment1HotMoiveAdapter: Fragment1HotMoiveAdapter)
    }
}


