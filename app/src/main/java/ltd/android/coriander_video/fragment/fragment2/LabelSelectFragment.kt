package ltd.android.coriander_video.fragment.fragment2

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.common_layout_recyclerview.*
import kotlinx.android.synthetic.main.fragment2_label_select.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.fragment2.label_select.Fragment2GuessLikeAdapter
import ltd.android.coriander_video.adapter.fragment2.label_select.Fragment2LabelAdapter
import ltd.android.coriander_video.adapter.fragment2.label_select.Fragment2LabelChildAdapter
import ltd.android.coriander_video.cofigure.AppConfigure
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.dto.TagDTO
import ltd.android.coriander_video.entity.Tag
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.request.LabelSelectRequest
import ltd.android.coriander_video.view_model.LabelSelectViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class LabelSelectFragment : BaseFragment<LabelSelectViewModel>() {

    override fun providerVMClass(): Class<LabelSelectViewModel> = LabelSelectViewModel::class.java

    override fun layoutId(): Int {
        return R.layout.fragment2_label_select
    }

    companion object {
        fun newInstance(): LabelSelectFragment {
            val fragment = LabelSelectFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private val mArrayListInteger = ArrayList<Int>()

    private val mTagDTOList = ArrayList<TagDTO>()
    private val mLabelAdapter = Fragment2LabelAdapter(mTagDTOList)

    private val mTagChildList = ArrayList<Tag>()
    private val mLabelChildAdapter = Fragment2LabelChildAdapter(mTagChildList)

    private val mMovieList = ArrayList<MovieDTO>()
    private val mGuessLikeAdapter = Fragment2GuessLikeAdapter(mMovieList)

    private var page = 1

    override fun initView() {
        super.initView()

        app_bar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, offset ->
            if (offset == appBarLayout.totalScrollRange) {
                this@LabelSelectFragment.tv_choose_label.visibility = View.VISIBLE
            } else {
                this@LabelSelectFragment.tv_choose_label.visibility = View.GONE
            }
        })

        this.tv_confirm.setOnClickListener {
            this@LabelSelectFragment.refreshLayout.autoRefresh()
            showMoiveView()
        }
        refreshLayout.setOnRefreshListener {
            val request = LabelSelectRequest()
            page = 1
            request.tagIds = mArrayListInteger
            request.page = page
            request.isRefresh = true;
            mViewModel?.getMoiveList(request,
                {},
                { refreshLayout.finishRefresh() })
        }

        refreshLayout.setOnLoadMoreListener {
            val request = LabelSelectRequest()
            page++
            request.tagIds = mArrayListInteger
            request.page = page
            request.isRefresh = false;
            mViewModel?.getMoiveList(request,
                {},
                { refreshLayout.finishLoadMore() })
        }


        rv_top.adapter = mLabelAdapter
        rv_top.layoutManager = GridLayoutManager(activity, 4)

        rv_top2.adapter = mLabelAdapter
        rv_top2.layoutManager = GridLayoutManager(activity, 4)

        mRecyclerViewRvChild.adapter = mLabelChildAdapter
        mRecyclerViewRvChild.layoutManager = GridLayoutManager(activity, 2)

        recyclerView.adapter = mGuessLikeAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, 1)


        mViewModel?.getTabList(
            {},
            {})

        mLabelAdapter.setOnItemClickListener { adapter, view, position ->
            Log.d("====", position.toString())
            val tagDTO = mTagDTOList[position]
            mLabelAdapter.selectedCount = position



            mMovieList.clear();
            mGuessLikeAdapter.notifyDataSetChanged()

            showTabView()

            if (position + 1 == mTagDTOList.size) {
                Log.d("====", "重置")
                mLabelAdapter.selectedCount = 0
                mArrayListInteger.clear()

                mTagDTOList.forEach {
                    it.isChecked = false
                    it.isSelected = false;
                    if (it.subclass != null)
                        it.subclass.forEach {
                            it.isSelected = false
                        }
                }

            } else {
                mTagChildList.clear();
                mTagChildList.addAll(tagDTO.subclass)

            }
            mLabelAdapter.notifyDataSetChanged()
            mLabelChildAdapter.notifyDataSetChanged()
        }

        mLabelChildAdapter.setOnItemClickListener { adapter, view, position ->

            if (mArrayListInteger.size >= 20) {
                //最多只能选择20个标签
                toastUtil.showToast("最多只能选择20个标签")

            } else {


                val tag = adapter.data[position] as Tag
                tag.isSelected = !tag.isSelected

                if (tag.isSelected) {
                    if (!mArrayListInteger.contains(tag.id)) {
                        mArrayListInteger.add(tag.id)
                    }
                }

                mTagDTOList[0].isSelected = false

                val selectedCount = mLabelAdapter.selectedCount
                mTagDTOList[selectedCount].isSelected = false
                //如果子项有一个被选中这显示父标签的打勾
                mTagChildList.forEach {
                    if (it.isSelected) {

                        mTagDTOList[selectedCount].isSelected = true
                    }
                }

                if (mArrayListInteger.size > 0) {
                    mTagDTOList[0].isSelected = true
                }


                mLabelAdapter.notifyDataSetChanged()
                adapter.notifyDataSetChanged()
                setTvConfirmVisibility()
            }


        }


    }

    private fun showTabView() {
        this@LabelSelectFragment.cl_search.visibility = View.VISIBLE
        this@LabelSelectFragment.cl_result.visibility = View.GONE
        this@LabelSelectFragment.tv_confirm.visibility = View.VISIBLE
    }

    private fun showMoiveView() {
        this@LabelSelectFragment.cl_search.visibility = View.GONE
        this@LabelSelectFragment.cl_result.visibility = View.VISIBLE
        this@LabelSelectFragment.tv_confirm.visibility = View.GONE
    }


    private fun setTvConfirmVisibility() {
        if (this.mArrayListInteger.size == 0) {
            this.tv_confirm.visibility = View.INVISIBLE
        } else {
            this.tv_confirm.visibility = View.VISIBLE
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mTagRespone.observe(this@LabelSelectFragment, Observer { tagRespone ->
                tagRespone?.let {
                    val allTagDTO = TagDTO()
                    allTagDTO.pname = "全部"
                    allTagDTO.subclass = ArrayList<Tag>()
                    allTagDTO.isChecked = true
                    mTagDTOList.add(allTagDTO)

                    it.mTagList.forEach {
                        //全部
                        allTagDTO.subclass.addAll(it.subclass)
                        mTagDTOList.add(it)
                    }
                    mTagChildList.addAll(allTagDTO.subclass)

                    //重置按钮
                    val resetTagDTO = TagDTO()
                    resetTagDTO.pname = "重置"
                    mTagDTOList.add(resetTagDTO)
                    mLabelAdapter.notifyDataSetChanged()
                }

            })
            mLabelSelectMoiveRespone.observe(this@LabelSelectFragment, Observer { labelSelectMoiveRespone ->
                labelSelectMoiveRespone?.let {
                    if (it.isRefresh) {
                        mMovieList.clear()
                    }

                    it.mMovieList.forEach {
                        mMovieList.add(it)
                    }

                    if (it.mMovieList.size < AppConfigure.globalPageSize) {
                        refreshLayout.finishLoadMoreWithNoMoreData()
                    }

                    mGuessLikeAdapter.notifyDataSetChanged()
                }

            })
        }


    }
}