package ltd.android.coriander_video.view_model

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.net.http.api.ZhuanLanAPi
import ltd.android.coriander_video.net.http.response.ZhuanLanRespone
import ltd.android.coriander_video.view_model.base.BaseViewModel

class ZhuanLanViewModel : BaseViewModel() {


    val mDiscover: MutableLiveData<ZhuanLanRespone> = MutableLiveData()
    /**
     * @param start 这个方法中可以显示加载进度条等
     * @param finally 可以隐藏进度条等
     */
    fun getZhuanLan(start: () -> Unit, finally: () -> Unit) {
        launchOnUITryCatch(
            {
                start()
                val zhuanLanResponse =
                    withContext(Dispatchers.IO) { ZhuanLanAPi.instance.getZhuanTiAsync() }.await()
                val hotStarResponse =
                    withContext(Dispatchers.IO) { ZhuanLanAPi.instance.getActorHotsAsync() }.await()

                if (zhuanLanResponse.success && hotStarResponse.success) {
                    val zhuanLanRespone = ZhuanLanRespone()
                    zhuanLanRespone.mNavList = zhuanLanResponse.data
                    zhuanLanRespone.hotStarList = hotStarResponse.data
                    mDiscover.value = zhuanLanRespone
                }
            }
            ,
            {
                Log.i("tt", "${it.message}")
            }, { finally() }, true
        )

    }

}