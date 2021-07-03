package ltd.android.coriander_video.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_play.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.adapter.fragment1.search.SearchAdapter
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.api.AdAPi
import ltd.android.coriander_video.net.http.api.FavAPi
import ltd.android.coriander_video.net.http.api.MovieAPi
import ltd.android.coriander_video.utils.ClipboardManagerUtils
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.video.callback.LocalGSYCallBack
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.Serializable


class VideoPlayActivity : BaseActivity<BaseViewModel>() {

    private var mTextViewTimeCountUtils = object : CountDownTimer(5000, 1000) {
        override fun onFinish() {
            ad_count_down.visibility = View.GONE
            video_ad.visibility = View.GONE
            video_player.visibility = View.VISIBLE
        }

        override fun onTick(millisUntilFinished: Long) {
            ad_count_down.text = (millisUntilFinished / 1000).toString() + "S"

        }

    }

    companion object {
        private const val dataName = "data"
        private val TAG = VideoPlayActivity.javaClass.simpleName
        fun start(context: Context?, data: MovieDTO) {
            val intent = Intent(context, VideoPlayActivity::class.java)
            intent.putExtra(dataName, data as Serializable)
            context?.startActivity(intent)
        }
    }

    lateinit var data: MovieDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return ltd.android.coriander_video.R.layout.activity_video_play
    }


    override fun initView() {
        super.initView()
        val intent = intent
        data = intent.getSerializableExtra(VideoPlayActivity.dataName) as MovieDTO
        initVideoPlay(data)
        full_detail.visibility = View.VISIBLE
        video_title_text.text = data.movName
        simple_common.text = data.movDesc

        ad_back.setOnClickListener {
            finish()
        }

        initAD()
        initAlike()
        initDetail()

    }

    private fun initDetail() {
        launch {
            val baseResponse =
                withContext(Dispatchers.IO) {
                    val mapOf = mapOf("id" to data.id)
                    MovieAPi.instance.getDetailAsync(mapOf)
                }.await()
            if (baseResponse.success) {
                val movieDTO = baseResponse.data
                video_upload_time.text = movieDTO.createDate
                video_play_times.text = movieDTO.playCountFormat


                share_image.setOnClickListener {
                    val userInfo = UserPrefsHelper.getInstance().userInfo!!
                    val text = userInfo.link.content

                    ClipboardManagerUtils.CopyToClipboard(it.context, text)
                    toastUtil.showToast("已复制内容到剪贴板")
                    share_image.setImageResource(R.drawable.send_press)

                }
                like_image.setOnClickListener {
                    GlobalScope.launch {
                        val response = withContext(Dispatchers.IO)
                        {
                            val mapOf = mapOf("id" to data.id)
                            FavAPi.instance.addAsync(mapOf)
                        }.await()
                    }

                    like_image.setImageResource(R.drawable.favor_press)
                }

                download_image.setOnClickListener {
                    download_image.setImageResource(R.drawable.download_press)
                    toastUtil.showToast("即将开始缓存")
                }

            }

        }
    }

    private fun initAD() {
        launch {
            val mapOf = mapOf("location" to 4.toString())
            val baseResponse =
                withContext(Dispatchers.IO) { AdAPi.instance.getAdAsync(mapOf) }.await()
            if (baseResponse.success) {
                mov_down_img.visibility = View.VISIBLE
                val index = (Math.random() * baseResponse.data.size).toInt()
                GlideUtils.loadImg(mov_down_img, baseResponse.data[index].thumbnail)
                mTextViewTimeCountUtils.start()
            }

        }

        launch {
            val mapOf = mapOf("location" to 5.toString())
            val baseResponse =
                withContext(Dispatchers.IO) { AdAPi.instance.getAdAsync(mapOf) }.await()
            if (baseResponse.success) {
                ad_image.visibility = View.VISIBLE
                val index = (Math.random() * baseResponse.data.size).toInt()
                GlideUtils.loadImg(ad_image, baseResponse.data[index].thumbnail)
            }

        }


    }

    private fun initAlike() {
        launch {
            val baseResponse =
                withContext(Dispatchers.IO) {
                    val mapOf = mapOf("id" to data.id)
                    MovieAPi.instance.getAlikeAsync(mapOf)
                }.await()
            if (baseResponse.success) {
                val mMovieList = ArrayList<MovieDTO>()
                val mAdapter = SearchAdapter(mMovieList)
                val mLinearLayoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(this@VideoPlayActivity)
                recyclerView.adapter = mAdapter
                recyclerView.layoutManager = mLinearLayoutManager
                mMovieList.addAll(baseResponse.data)
                mAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun initVideoPlay(data: MovieDTO) {
        video_player.visibility = View.GONE

        val imageView = ImageView(this@VideoPlayActivity)
        imageView.setImageResource(ltd.android.coriander_video.R.mipmap.ic_launcher)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideUtils.loadImg(imageView, data.cover)
        val mGSYVideoOptionBuilder = GSYVideoOptionBuilder()
        mGSYVideoOptionBuilder
            .setIsTouchWiget(true)
            .setThumbImageView(imageView)
            .setUrl(data.file)
            .setVideoTitle(data.movName)
            .setCacheWithPlay(false)
            .setRotateViewAuto(true)
            .setLockLand(true)
            .setPlayTag(TAG)
            .setShowFullAnimation(true)
            .setNeedLockFull(true)
            .setPlayPosition(0)
            .setVideoAllCallBack(LocalGSYCallBack(video_player, data)).build(video_player)

        //增加title
        video_player.titleTextView.visibility = View.VISIBLE

        //设置返回键
        video_player.backButton.visibility = View.VISIBLE

        //设置全屏按键功能
        video_player.fullscreenButton
            .setOnClickListener {
                resolveFullBtn(it.context, video_player)
            }
    }

    /**
     * 全屏幕按键处理
     */
    private fun resolveFullBtn(context: Context, standardGSYVideoPlayer: StandardGSYVideoPlayer) {
        standardGSYVideoPlayer.startWindowFullscreen(context, true, true)
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
        mTextViewTimeCountUtils.cancel()
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }

        super.onBackPressed()
    }


    //    override  fun onBackPressed(): Boolean {
//        return GSYVideoManager.backFromWindowFull(activity)
//    }
}


