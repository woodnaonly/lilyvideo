package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.fragment1.search.LoveAdapter
import ltd.android.coriander_video.adapter.fragment1.search.SearchAdapter
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.view_model.SearchViewModel


class SearchActivity : BaseActivity<SearchViewModel>() {
    override fun providerVMClass(): Class<SearchViewModel> = SearchViewModel::class.java

    private val mMovieList = ArrayList<MovieDTO>()
    private val mAdapter = SearchAdapter(mMovieList)
    private val mLoveAdapter = LoveAdapter(mMovieList)


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

        val label: ArrayList<String> = ArrayList()
        label.add("Android")
        label.add("IOS")
        label.add("前端")
        label.add("微信开发")
        label.add("微信开发")
        label.add("微信开发")
        label.add("微信开发")
        mHotLabel.setLabels(label) //直接设置一个字符串数组就可以了
        mHistoryLabel.setLabels(label) //直接设置一个字符串数组就可以了

        mRvLove.layoutManager = GridLayoutManager(this, 2)
        mRvLove.adapter = mLoveAdapter;
        val movieDTO1 = MovieDTO()
        movieDTO1.cover =
            "https://img0.baidu.com/it/u=298549842,2970468945&fm=26&fmt=auto&gp=0.jpg"
        mMovieList.add(movieDTO1)
        mMovieList.add(movieDTO1)
        mMovieList.add(movieDTO1)
        mMovieList.add(movieDTO1)
        mLoveAdapter.notifyDataSetChanged()
    }


    override fun startObserve() {
        super.startObserve()
        mViewModel?.apply {
            mDiscover.observe(this@SearchActivity, Observer { list ->
                list?.let {

                }
                mAdapter.notifyDataSetChanged()
            })
        }
    }

}


