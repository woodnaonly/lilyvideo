package ltd.android.coriander_video.fragment.fragment1

import androidx.lifecycle.Observer
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bytedance.tiktok.view.ControllerView
import kotlinx.android.synthetic.main.fragment1_children1.*
import kotlinx.android.synthetic.main.item_fragment1_children.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.user.UpInfoActivity
import ltd.android.coriander_video.adapter.fragment1.Fragment1ChildrenAdapter
import ltd.android.coriander_video.entity.VideoBean
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.utils.ToastUtil
import ltd.android.coriander_video.view.FullScreenVideoView
import ltd.android.coriander_video.view.LikeView
import ltd.android.coriander_video.view.OnVideoControllerListener
import ltd.android.coriander_video.view.viewpagerlayoutmanager.OnViewPagerListener
import ltd.android.coriander_video.view.viewpagerlayoutmanager.ViewPagerLayoutManager
import ltd.android.coriander_video.view_model.HomeChildrenModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class HomeChildrenFragment1 : BaseFragment<HomeChildrenModel>() {

    override fun providerVMClass(): Class<HomeChildrenModel> = HomeChildrenModel::class.java

    private val mListEntity = ArrayList<VideoBean>()
    private val mAdapter = Fragment1ChildrenAdapter(mListEntity)

    private lateinit var mViewPagerLayoutManager: ViewPagerLayoutManager
    private lateinit var mFullScreenVideoView: FullScreenVideoView

    private var ivCurCover: ImageView? = null

    /** 当前播放视频位置  */
    private var curPlayPos = -1

    override fun layoutId(): Int {
        return R.layout.fragment1_children1;
    }

    companion object {
        fun newInstance(): HomeChildrenFragment1 {
            val fragment = HomeChildrenFragment1()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        mFullScreenVideoView.start()
    }

    override fun onStop() {
        super.onStop()
        mFullScreenVideoView.stopPlayback()
    }


    override fun initView() {
        super.initView()
        mFullScreenVideoView = FullScreenVideoView(activity)
        setRefreshEvent()
        initRecyclerView()
        setFragments()
    }


    private fun playCurVideo(position: Int) {
        if (position == curPlayPos) {
            return
        }
        val itemView = mViewPagerLayoutManager.findViewByPosition(position) ?: return
        val rootView = itemView.findViewById<ViewGroup>(R.id.rl_container)
        val likeView: LikeView = rootView.findViewById(R.id.mLikeView)
        val controllerView: ControllerView = rootView.findViewById(R.id.mControllerView)
        val ivPlay = rootView.findViewById<ImageView>(R.id.iv_play)
        val ivCover = rootView.findViewById<ImageView>(R.id.iv_cover)
        ivPlay.alpha = 0.4f
        //评论点赞事件
        likeShareEvent(controllerView)
        //播放暂停事件
        likeView.setOnPlayPauseListener(object : LikeView.OnPlayPauseListener {
            override fun onPlayOrPause() {
                if (mFullScreenVideoView.isPlaying) {
                    mFullScreenVideoView.pause()
                    ivPlay.visibility = View.VISIBLE
                } else {
                    mFullScreenVideoView.start()
                    ivPlay.visibility = View.GONE
                }
            }

        })

        //评论点赞事件
//        likeShareEvent(controllerView)

        //切换播放视频的作者主页数据

        curPlayPos = position

        //切换播放器位置
        dettachParentView(rootView)
        autoPlayVideo(curPlayPos, ivCover)
    }

    /**
     * 移除videoview父view
     */
    private fun dettachParentView(rootView: ViewGroup) {
        //1.添加videoview到当前需要播放的item中,添加进item之前，保证ijkVideoView没有父view
        mFullScreenVideoView.parent?.let {
            (it as ViewGroup).removeView(mFullScreenVideoView)
        }
        rootView.addView(mFullScreenVideoView, 0)
    }

    /**
     * 自动播放视频
     */
    private fun autoPlayVideo(position: Int, ivCover: ImageView) {
        mFullScreenVideoView.setVideoURI(Uri.parse(mListEntity[position].videoURL))
        mFullScreenVideoView.start()
        mFullScreenVideoView.setOnPreparedListener { mp: MediaPlayer ->
            mp.isLooping = true

            //延迟取消封面，避免加载视频黑屏
            object : CountDownTimer(200, 200) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    ivCover.visibility = View.GONE
                    ivCurCover = ivCover
                }
            }.start()
        }
    }

    private fun initRecyclerView() {
        mViewPagerLayoutManager = ViewPagerLayoutManager(context)
        mRecyclerView.layoutManager = mViewPagerLayoutManager
        mRecyclerView.adapter = mAdapter
        mViewPagerLayoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                playCurVideo(0)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                if (ivCurCover != null) {
                    ivCurCover!!.visibility = View.VISIBLE
                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                playCurVideo(position)
            }
        })

    }

    private fun setRefreshEvent() {
        mSwipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        mSwipeRefreshLayout.post {
            mSwipeRefreshLayout.setRefreshing(true);
            onRefresh()
        }
    }

    private fun onRefresh() {
        mViewModel?.getData(
            {

            }, {
                mSwipeRefreshLayout.isRefreshing = false
            }
        )
    }

    private fun setFragments() {
        val mapOf = mapOf<String, String>()

    }

    /**
     * 用户操作事件
     */
    private fun likeShareEvent(controllerView: ControllerView) {
        controllerView.setListener(object : OnVideoControllerListener {
            override fun onHeadClick() {
//                RxBus.getDefault().post(MainPageChangeEvent(1))
                UpInfoActivity.start(context,mListEntity[curPlayPos].userBean)

            }

            override fun onLikeClick() {

            }

            override fun onCommentClick() {
                ToastUtil.getInstance().showToast("点击评论")
//                val commentDialog = CommentDialog()
//                commentDialog.show(childFragmentManager, "")
            }

            override fun onShareClick() {
                ToastUtil.getInstance().showToast("点击分享")
//                ShareDialog().show(childFragmentManager, "")
            }
        })
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mHomeRespone.observe(this@HomeChildrenFragment1, Observer { respone ->
//                respone?.let {
//                    val data = it.data
//                    if (it.isRefresh) {
//                        mListEntity.clear()
//                    }
//                    data.forEach {
//                        mListEntity.add(Fragment3VideoEntity(it))
//                    }
//                }
                mListEntity.clear()
                mListEntity.addAll(respone!!.mList)
                mAdapter.notifyDataSetChanged()
            })
        }

    }
}