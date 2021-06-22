package ltd.android.coriander_video.fragment.classify_grid

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_classify_grid_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.ClassifyAdapter
import ltd.android.coriander_video.cofigure.AppConfigure
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.MovieAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class RecyclerAVClassifyGridFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.fragment_classify_grid_fragment;
    }

    var clsId: Long = 0
    var page: Int = 1

    var sort = ""

    private val mDataList = ArrayList<MovieDTO>()
    private val mAdapter = ClassifyAdapter(mDataList)
    private lateinit var mGridLayoutManager: GridLayoutManager

    companion object {
        private const val dataName = "data"

        fun newInstance(Id: Long): RecyclerAVClassifyGridFragment {
            val fragment = RecyclerAVClassifyGridFragment()
            val args = Bundle()
            args.putLong(dataName, Id)
            fragment.arguments = args
            return fragment
        }
    }

    fun Refresh() {
        if (mSmartRefreshLayout != null)
            mSmartRefreshLayout.autoRefresh()
    }

    override fun initView() {
        super.initView()
        mGridLayoutManager = GridLayoutManager(context, 2)

        mRecyclerView.adapter = mAdapter

        mRecyclerView.layoutManager = mGridLayoutManager

        mSmartRefreshLayout.setOnRefreshListener {
            page = 1
            getList(true)
        }

        mSmartRefreshLayout.setOnLoadMoreListener {
            page++
            getList(false)
        }
        mSmartRefreshLayout.autoRefresh()
    }

    private fun getList(refresh: Boolean) {
        launch {
            val baseResponse =
                withContext(Dispatchers.IO) {
                    val hashMap = HashMap<String, Any>()
                    if (clsId != -1024L) {
                        hashMap["clsId"] = clsId
                    }
                    if (!sort.isEmpty()) {
                        hashMap["sort"] = sort
                    }

                    hashMap["page"] = page
                    hashMap["pageSize"] = AppConfigure.globalPageSize

                    MovieAPi.instance.getListAsync(hashMap)
                }.await()
            if (baseResponse.success) {
                if (refresh) {
                    mDataList.clear()
                    if (mSmartRefreshLayout != null)
                        mSmartRefreshLayout.finishRefresh()
                } else {
                    if (mSmartRefreshLayout != null)
                        mSmartRefreshLayout.finishLoadMore()
                }

                mDataList.addAll(baseResponse.data)
                mAdapter.notifyDataSetChanged()
                if (baseResponse.data.size < AppConfigure.globalPageSize) {
                    mSmartRefreshLayout.finishLoadMoreWithNoMoreData()
                }
            }

        }
    }

    override fun prepareBeforeInitView() {
        super.prepareBeforeInitView()
        clsId = arguments?.getLong(dataName)!!
    }
}