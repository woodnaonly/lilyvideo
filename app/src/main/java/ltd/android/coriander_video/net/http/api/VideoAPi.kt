package ltd.android.coriander_video.net.http.api

import ltd.android.coriander_video.entity.VideoBean
import ltd.android.coriander_video.net.http.ApiBuild
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Suppress("unused")
object VideoAPi {
    val instance: Service by lazy { ApiBuild.createApi(VideoAPi.Service::class.java) }


    interface Service {

        @GET("https://tb.lxlx.fun/api/1.json")
        suspend fun getHomeTest(@QueryMap map: Map<String, String>): List<VideoBean>

        companion object {
            const val ModuleName = "api/"
        }

    }


}
