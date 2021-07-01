package ltd.android.coriander_video.view_model

import androidx.lifecycle.MutableLiveData
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.net.http.api.AdAPi
import ltd.android.coriander_video.net.http.api.HomeAPi
import ltd.android.coriander_video.net.http.response.HomeRespone
import ltd.android.coriander_video.view_model.base.BaseViewModel

class HomeViewModel : BaseViewModel() {


    val mHomeRespone: MutableLiveData<HomeRespone> = MutableLiveData()
    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getAD(start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {

                val map = mapOf(
                    "location" to "1"
                )
                start()
                val adResponseDeferred =
                    withContext(Dispatchers.IO) { AdAPi.instance.getAdAsync(map) }

                val movieClassResponseDeferred =
                    withContext(Dispatchers.IO) { HomeAPi.instance.getMovieClassAsync() }

                val getNewMovAsyncResponseDeferred =
                    withContext(Dispatchers.IO) { HomeAPi.instance.getNewMovAsync() }

                val gethotMovAsyncResponseDeferred =
                    withContext(Dispatchers.IO) {
                        val map = mapOf(
                            "page" to "1"
                        )
                        HomeAPi.instance.gethotMovAsync(map)
                    }


                val getGuessLikeAsync =
                    withContext(Dispatchers.IO) { HomeAPi.instance.getGuessLikeAsync() }

                val getColumnsAsync =
                    withContext(Dispatchers.IO) { HomeAPi.instance.getColumnsAsync() }


                val adResponse = adResponseDeferred.await()
                val movieClassResponse = movieClassResponseDeferred.await()
                val newMovResponse = getNewMovAsyncResponseDeferred.await()
                val hotMovResponse = gethotMovAsyncResponseDeferred.await()
                val getGuessLikeResponse = getGuessLikeAsync.await()
                val getColumnsResponse = getColumnsAsync.await()

                val homeResponse = HomeRespone()
                homeResponse.movieClassList = movieClassResponse.data
                homeResponse.columnsList = getColumnsResponse.data
                homeResponse.guessLikeList = getGuessLikeResponse.data
                homeResponse.hotMovList = hotMovResponse.data
                homeResponse.adList = adResponse.data
                homeResponse.newmovList = newMovResponse.data

                mHomeRespone.value = homeResponse


            }
            ,
            {
                Log.i("tt", "${it.message}")
            }, { finally() }, true
        )

    }

}