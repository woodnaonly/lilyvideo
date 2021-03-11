package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.TagDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET

@Suppress("unused")
object LabelSelectAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }

    interface Service {
        @GET(ModuleName + "list")
        @JvmSuppressWildcards
        fun getTagListAsync(): Deferred<BaseResponse<List<TagDTO>>>

        companion object {
            const val ModuleName = "api/tag/"
        }

    }
}
