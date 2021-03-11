package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.request.LabelSelectRequest
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

@Suppress("unused")
object MovieAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {


        @GET(ModuleName + "discover")
        @JvmSuppressWildcards
        fun getDiscoverAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>


        @POST(ModuleName + "bytags")
        @JvmSuppressWildcards
        fun getMovieByTagsAsync(@Body request: LabelSelectRequest): Deferred<BaseResponse<List<MovieDTO>>>


        @GET(ModuleName + "search")
        @JvmSuppressWildcards
        fun getSearchAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>

        @GET(ModuleName + "list")
        @JvmSuppressWildcards
        fun getListAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>

        @GET(ModuleName + "alike")
        @JvmSuppressWildcards
        fun getAlikeAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>


        @GET(ModuleName + "detail")
        @JvmSuppressWildcards
        fun getDetailAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<MovieDTO>>




        companion object {

            const val ModuleName = "api/movie/"
        }

    }
}
