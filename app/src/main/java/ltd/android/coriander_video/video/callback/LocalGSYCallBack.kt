package ltd.android.coriander_video.video.callback

import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.utils.ObjectBox
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.video.SampleCoverVideo

/**
 *
 *
 * @author by 黄梦 on 2019/3/19.
 */
class LocalGSYCallBack(private val videoPlayerView: SampleCoverVideo, private val movieDTO: MovieDTO) :
    GSYSampleCallBack() {

    private var mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor()


    override fun onPrepared(url: String?, vararg objects: Any) {
        super.onPrepared(url, *objects)
        if (!videoPlayerView.isIfCurrentIsFullscreen) {
            //静音
            GSYVideoManager.instance().isNeedMute = true
        }
        val userInfo = UserPrefsHelper.getInstance().userInfo!!
        movieDTO.lastTime = System.currentTimeMillis()
        movieDTO.UserId = userInfo.id
        mBox.put(movieDTO)
    }

    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
        super.onQuitFullscreen(url, *objects)
        //全屏不静音
        GSYVideoManager.instance().isNeedMute = true
    }

    override fun onEnterFullscreen(url: String?, vararg objects: Any) {
        super.onEnterFullscreen(url, *objects)
        GSYVideoManager.instance().isNeedMute = false
        videoPlayerView.currentPlayer.titleTextView.text = objects[0] as String
    }
}