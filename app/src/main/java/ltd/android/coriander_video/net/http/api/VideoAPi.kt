package ltd.android.coriander_video.net.http.api

import ltd.android.coriander_video.entity.VideoBean
import ltd.android.coriander_video.net.http.ApiBuild
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Suppress("unused")
object VideoAPi {
    val instance: Service by lazy { ApiBuild.createApi(VideoAPi.Service::class.java) }


    interface Service {

        @GET("https://gitee.com/woodnaonly/hello-world/raw/master/%E5%A4%96%E5%8C%85/%E6%8A%96%E9%9F%B3.json")
        suspend fun getHomeTest(@QueryMap map: Map<String, String>): List<VideoBean>

        companion object {
            const val ModuleName = "api/"
        }

    }


}
