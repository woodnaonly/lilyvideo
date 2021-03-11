package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.NoticeDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET

@Suppress("unused")
object NoticeAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {

        @GET(ModuleName + "index_123123")
        fun getAdAsync(): Deferred<BaseResponse<NoticeDTO>>


        @GET(ModuleName + "list")
        fun getNotifiListAsync(): Deferred<BaseResponse<List<NoticeDTO>>>

        companion object {
            const val ModuleName = "api/notice/"
        }

    }
}
