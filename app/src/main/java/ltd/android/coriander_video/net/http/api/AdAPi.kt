package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.entity.Ad
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Suppress("unused")
object AdAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {

        @GET(ModuleName + "ads")
        fun getAdAsync(@QueryMap map: Map<String, String>): Deferred<BaseResponse<List<Ad>>>
        companion object {
            const val ModuleName = "api/"
        }

    }
}
