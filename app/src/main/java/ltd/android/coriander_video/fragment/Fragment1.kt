package ltd.android.coriander_video.fragment

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.common_layout_recyclerview.*
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.HistoryActivity
import ltd.android.coriander_video.activity.ScanActivity
import ltd.android.coriander_video.activity.SearchActivity
import ltd.android.coriander_video.adapter.fragment1.Fragment1Adapter
import ltd.android.coriander_video.adapter.fragment1.Fragment1HotMoiveAdapter
import ltd.android.coriander_video.adapter.fragment1.entity.*
import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.HomeAPi
import ltd.android.coriander_video.view_model.HomeViewModel
import java.util.*

/**
 *
 *
 * @author by 梁馨 on 2019/2/28.
 */
class Fragment1 : BaseFragment<HomeViewModel>() {

    override fun providerVMClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private val mListEntity = ArrayList<Fragment1EntityBase>()
    private val mAdapter = Fragment1Adapter(mListEntity)
    private val mLinearLayoutManager = LinearLayoutManager(context)

    override fun layoutId(): Int {
        return R.layout.fragment1;
    }

    companion object {
        fun newInstance(): Fragment1 {
            val fragment = Fragment1()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.setBackgroundResource(R.color.color_252627)

        refreshLayout.setOnRefreshListener {
            mViewModel?.getAD({}, {
                refreshLayout.finishRefresh()
                refreshLayout.finishLoadMoreWithNoMoreData()
            })
        }
        refreshLayout.autoRefresh()
        refreshLayout.isEnableLoadMore = false

        main_scan.setOnClickListener {
            startActivity(Intent(activity, ScanActivity::class.java))
        }
        main_search.setOnClickListener {
            SearchActivity.start(activity)
        }

        mian_search_text.setOnClickListener {
            main_search.performClick()
        }

        main_history.setOnClickListener {
            HistoryActivity.start(activity)
        }
        mAdapter.mChangeContentListener = object : Fragment1Adapter.ChangeContentListener {
            override fun onClick(fragment1HotMoiveAdapter: Fragment1HotMoiveAdapter) {
                launch {
                    val baseResponse =
                        withContext(Dispatchers.IO) {
                            val map = mapOf(
                                "page" to Random().nextInt(4)
                            )
                            HomeAPi.instance.gethotMovAsync(map)
                        }.await()
                    if (baseResponse.success) {
                        fragment1HotMoiveAdapter.setNewData(baseResponse.data)
                    }
                }
            }
        }

    }

    override fun startObserve() {
        super.startObserve()
//        mViewModel?.apply {
//            mHomeRespone.observe(this@Fragment1, Observer {
//                it?.let {
//                    it.adList
//                }
//            })
//        }


        val mHomeRespone = mViewModel?.mHomeRespone
        mHomeRespone?.observe(this@Fragment1, Observer {
            it?.let {
                mListEntity.clear()
                mListEntity.add(Fragment1BannerEntity(it.adList))
                mListEntity.add(Fragment1MoiveClassEntity(it.movieClassList))
                mListEntity.add(Fragment1NewMoiveEntity(it.newmovList))
                mListEntity.add(Fragment1DivideLineEntity())
                mListEntity.add(Fragment1HotMoiveEntity(it.hotMovList))
                mListEntity.add(Fragment1DivideLineEntity())
                mListEntity.add(Fragment1GuessLikeEntity(it.guessLikeList))
                it.columnsList.forEach {
                    mListEntity.add(Fragment1DivideLineEntity())
                    mListEntity.add(Fragment1ColumnsMoiveEntity(it))
                }

//                refreshLayout.finishLoadMoreWithNoMoreData()
            }

        })

    }
}