package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.NavActivityAdapter
import ltd.android.coriander_video.dto.ColumnNavDTO
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.api.ZhuanLanAPi
import ltd.android.coriander_video.utils.DisplayMetricsUtils
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.Serializable

class NavActivity : BaseActivity<BaseViewModel>() {

    companion object {
        private const val dataName = "data"

        fun start(context: Context?, data: ColumnNavDTO.Nav) {
            val intent = Intent(context, NavActivity::class.java)
            intent.putExtra(dataName, data as Serializable)
            context?.startActivity(intent)
        }
    }

    private val mDataList = ArrayList<MovieDTO>()
    private val mAdapter = NavActivityAdapter(mDataList)
    private val mGridLayoutManager =
        androidx.recyclerview.widget.GridLayoutManager(this, 3)

    override fun layoutId(): Int {
        return R.layout.activity_nav
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        super.initView()
        val intent = intent
        val data = intent.getSerializableExtra(NavActivity.dataName) as ColumnNavDTO.Nav
        nav_back_button.setOnClickListener { finish() }
        GlideUtils.loadImg(nav_back, data.navImage)
        nav_name.text = data.navName
        nav_intro.text = data.intro

        val windowWidth = DisplayMetricsUtils.getWindowWidth().toInt()
        val layoutParams = RelativeLayout.LayoutParams(windowWidth, windowWidth)
        nav_back_top.layoutParams = layoutParams
        nav_back.layoutParams = layoutParams

        mRecyclerView.layoutManager = mGridLayoutManager
        mRecyclerView.adapter = mAdapter

        launch {
            val response = withContext(Dispatchers.IO)
            {
                val mapOf = mapOf("navId" to data.id)
                ZhuanLanAPi.instance.getcolumnMoviesAsync(mapOf)
            }.await()
            if (response.success) {
                mDataList.addAll(response.data)
            }
            mAdapter.notifyDataSetChanged()

        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}


