package ltd.android.coriander_video.view_model

import androidx.lifecycle.MutableLiveData
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.net.http.api.LabelSelectAPi
import ltd.android.coriander_video.net.http.api.MovieAPi
import ltd.android.coriander_video.net.http.request.LabelSelectRequest
import ltd.android.coriander_video.net.http.response.LabelSelectMoiveRespone
import ltd.android.coriander_video.net.http.response.TagRespone
import ltd.android.coriander_video.view_model.base.BaseViewModel

class LabelSelectViewModel : BaseViewModel() {


    val mTagRespone: MutableLiveData<TagRespone> = MutableLiveData()
    val mLabelSelectMoiveRespone: MutableLiveData<LabelSelectMoiveRespone> = MutableLiveData()


    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getTabList(start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {
                start()
                val tabResponse =
                    withContext(Dispatchers.IO) { LabelSelectAPi.instance.getTagListAsync() }.await()
                if (tabResponse.success) {
                    val tagRespone = TagRespone()
                    tagRespone.mTagList = tabResponse.data
                    mTagRespone.value = tagRespone
                }
            }
            ,
            {
                Log.i("tt", "${it.message}")
            }, { finally() }, true
        )

    }

    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getMoiveList(request: LabelSelectRequest, start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {
                start()
                val respone =
                    withContext(Dispatchers.IO) { MovieAPi.instance.getMovieByTagsAsync(request) }.await()
                if (respone.success) {
                    val labelSelectMoiveRespone = LabelSelectMoiveRespone()
                    labelSelectMoiveRespone.mMovieList = respone.data
                    labelSelectMoiveRespone.isRefresh = request.isRefresh
                    mLabelSelectMoiveRespone.value = labelSelectMoiveRespone
                }
            }
            ,
            {
                Log.i("tt", "${it.message}")
            }, { finally() }, true
        )

    }
}