package ltd.android.coriander_video.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import kotlinx.android.synthetic.main.fragment3.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.SearchActivity
import ltd.android.coriander_video.adapter.fragment3.Fragment3Adapter
import ltd.android.coriander_video.adapter.fragment3.entity.Fragment3VideoEntity
import ltd.android.coriander_video.adapter.fragment3.entity.base.Fragment3EntityBase
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.view_model.MovieViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class Fragment3 : BaseFragment<MovieViewModel>() {
    override fun providerVMClass(): Class<MovieViewModel> = MovieViewModel::class.java

    private val mListEntity = ArrayList<Fragment3EntityBase>()
    private val mAdapter = Fragment3Adapter(mListEntity)
    private val mLinearLayoutManager = LinearLayoutManager(context)


    override fun layoutId(): Int {
        return R.layout.fragment3
    }

    private var page = 1

    companion object {
        fun newInstance(): Fragment3 {
            val fragment = Fragment3()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mAdapter

        discover_search.setOnClickListener {
            SearchActivity.start(activity)
        }

//        mTextView.setOnClickListener {
//
//            mViewModel?.getWeather(1, {}, {})
//
//        }
        mSmartRefreshLayout.setOnRefreshListener {
            mViewModel?.getMovie(1, true, {
                page = 1
            }, {
                mSmartRefreshLayout.finishRefresh()
            })
        }

        mSmartRefreshLayout.setOnLoadMoreListener {
            mViewModel?.getMovie(++page, false, {}, {
                mSmartRefreshLayout.finishLoadMore()
            })
        }
        mSmartRefreshLayout.autoRefresh()

    }

    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mDiscover.observe(this@Fragment3, Observer { list ->
                list?.let {
                    val data = it.data
                    if (it.isRefresh) {
                        mListEntity.clear()
                    }
                    data.forEach {
                        mListEntity.add(Fragment3VideoEntity(it))
                    }
                }
                mAdapter.notifyDataSetChanged()
            })
        }
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }

    fun onBackPressed(): Boolean {
        return GSYVideoManager.backFromWindowFull(activity)
    }
}