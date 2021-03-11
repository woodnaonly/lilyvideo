package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.*

@Suppress("unused")

object FavAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {
        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "add")
        fun addAsync(@FieldMap map: Map<String, Any>): Deferred<BaseResponse<Any>>

        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "del")
        fun delAsync(@FieldMap map: Map<String, Any>): Deferred<BaseResponse<Any>>

        @JvmSuppressWildcards
        @GET(ModuleName + "list")
        fun getListAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>

        companion object {
            const val ModuleName = "api/fav/"
        }

    }
}
