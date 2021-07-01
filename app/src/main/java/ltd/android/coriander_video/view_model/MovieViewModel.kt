package ltd.android.coriander_video.view_model
import androidx.lifecycle.MutableLiveData
import android.util.Log
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.api.MovieAPi
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import ltd.android.coriander_video.view_model.base.BaseViewModel

class MovieViewModel : BaseViewModel() {


    val mDiscover: MutableLiveData<BaseResponse<List<MovieDTO>>> = MutableLiveData()
    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getMovie(page: Int, isRefresh: Boolean, start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {
                val map = mapOf(
                    "pageSize" to 20,
                    "page" to page
                )
                start()
                val baseResponse = withContext(IO) { MovieAPi.instance.getDiscoverAsync(map) }.await()
                if (baseResponse.success) {
                    baseResponse.isRefresh = isRefresh
                    mDiscover.value = baseResponse
                }
            }
            ,
            {
                Log.i("tt", "${it.message}")
            }, { finally() }, true
        )

    }

}