package ltd.android.coriander_video.view_model

import androidx.lifecycle.MutableLiveData
import android.util.Log
import ltd.android.coriander_video.net.http.api.VideoAPi
import ltd.android.coriander_video.net.http.response.HomeChildrenRespone
import ltd.android.coriander_video.view_model.base.BaseViewModel

class HomeChildrenModel : BaseViewModel() {


    val mHomeRespone: MutableLiveData<HomeChildrenRespone> = MutableLiveData()



    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getData(start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {

                val map = mapOf(
                    "location" to "1"
                )
                start()
                val list = VideoAPi.instance.getHomeTest(map)

                val homeChildrenRespone = HomeChildrenRespone()
                homeChildrenRespone.mList = list
                mHomeRespone.value = homeChildrenRespone


            },
            {
                Log.i("tt111", "${it.message}")
            }, {
                finally()
            }, true
        )

    }

}