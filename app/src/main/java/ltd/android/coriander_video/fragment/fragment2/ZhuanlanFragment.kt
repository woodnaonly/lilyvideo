package ltd.android.coriander_video.fragment.fragment2

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment2_zhuanlan.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.fragment2.Fragment2ZhuanLanAdapter
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2HotNavEntity
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2HotStarEntity
import ltd.android.coriander_video.adapter.fragment2.entity.Fragment2MustSeeNavEntity
import ltd.android.coriander_video.adapter.fragment2.entity.FragmentLableEntity
import ltd.android.coriander_video.adapter.fragment2.entity.base.Fragment2EntityBase
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.view_model.ZhuanLanViewModel

/**
 *
 *
 * @author by 梁馨 on 2019/2/28.
 */
class ZhuanlanFragment : BaseFragment<ZhuanLanViewModel>() {

    override fun providerVMClass(): Class<ZhuanLanViewModel> = ZhuanLanViewModel::class.java

    override fun layoutId(): Int {
        return R.layout.fragment2_zhuanlan;
    }

    private val mListEntity = ArrayList<Fragment2EntityBase>()
    private val mAdapter = Fragment2ZhuanLanAdapter(mListEntity)
    private val mLinearLayoutManager = LinearLayoutManager(context)


    companion object {
        fun newInstance(): ZhuanlanFragment {
            val fragment = ZhuanlanFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mAdapter
        mViewModel?.getZhuanLan({

        }, {
        })

        mSmartRefreshLayout.setOnRefreshListener {
            mViewModel?.getZhuanLan({

            }, {
                mSmartRefreshLayout.finishRefresh()
            })
        }
        mSmartRefreshLayout.isEnableLoadMore=false

        mSmartRefreshLayout.autoRefresh()

    }

    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mDiscover.observe(this@ZhuanlanFragment, Observer { list ->
                list?.let {
                    val data = it
                    mListEntity.clear()
                    data.mNavList.forEachIndexed { index, column ->

                        if (index == 0) {
//                            mListEntity.add(FragmentLableEntity(column.modName))
                            mListEntity.add(Fragment2MustSeeNavEntity(column))
                        }
                        if (index == 1) {
//                            mListEntity.add(FragmentLableEntity(column.modName))
                            mListEntity.add(Fragment2HotNavEntity(column))
                        }

                    }
                    mListEntity.add(FragmentLableEntity("人气女优"))
                    data.hotStarList.forEach {
                        mListEntity.add(Fragment2HotStarEntity(it))
                    }

                }
                mAdapter.notifyDataSetChanged()
            })
        }
    }


}