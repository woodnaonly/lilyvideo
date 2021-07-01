package ltd.android.coriander_video.activity

import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_search.*
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.fragment2.label_select.SearchAdapter
import ltd.android.coriander_video.cofigure.AppConfigure
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.view_model.SearchViewModel


class SearchActivity : BaseActivity<SearchViewModel>() {
    override fun providerVMClass(): Class<SearchViewModel> = SearchViewModel::class.java

    private val mMovieList = ArrayList<MovieDTO>()
    private val mAdapter = SearchAdapter(mMovieList)
    private val mLinearLayoutManager =
        androidx.recyclerview.widget.LinearLayoutManager(this)
    private var page = 1
    private var keyword: String? = null


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, SearchActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return ltd.android.coriander_video.R.layout.activity_search
    }


    override fun initView() {
        super.initView()
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mAdapter
        mSmartRefreshLayout.setOnRefreshListener {
            mViewModel?.getMovie(1, keyword!!, true, {
                page = 1
            }, {
                mSmartRefreshLayout.finishRefresh()
            })
        }

        mSmartRefreshLayout.setOnLoadMoreListener {
            mViewModel?.getMovie(++page, keyword!!, false, {}, {
                mSmartRefreshLayout.finishLoadMore()
            })
        }
        mian_search_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > 0) {
                    back_button.visibility = View.VISIBLE
                } else {
                    back_button.visibility = View.GONE
                }

            }

        })

        back_button.setOnClickListener {
            mian_search_text.text = null
        }

        cancel_button.setOnClickListener {
            finish()
        }


        mian_search_text.setOnEditorActionListener { v, actionId, event ->
            if (actionId != EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener false
            }
            val trim = mian_search_text.text.toString().trim()
            if (TextUtils.isEmpty(trim)) {
                return@setOnEditorActionListener true
            }
            keyword = trim
            if (trim.length > 20) {
                toastUtil.showToast("不能超过20个字符")
                return@setOnEditorActionListener true
            } else {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(mian_search_text.windowToken, 0) //强制隐藏键盘
                mSmartRefreshLayout.autoRefresh()
                return@setOnEditorActionListener true
            }


        }

    }


    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mDiscover.observe(this@SearchActivity, Observer { list ->
                list?.let {
                    val data = it.data
                    if (it.isRefresh) {
                        mMovieList.clear()
                    }
                    data.forEach {
                        mMovieList.add(it)
                    }
                    if (data.size < AppConfigure.globalPageSize) {
                        mSmartRefreshLayout.finishLoadMoreWithNoMoreData()
                    }
                }
                mAdapter.notifyDataSetChanged()
            })
        }
    }

}


