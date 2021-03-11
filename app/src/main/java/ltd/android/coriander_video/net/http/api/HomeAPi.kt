package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.ColumnMovieDTO
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.entity.MovieClass
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Suppress("unused")
object HomeAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {

        @GET( "api/movie-class/list")
        @JvmSuppressWildcards
        fun getMovieClassAsync(): Deferred<BaseResponse<MutableList<MovieClass>>>

        //获取首页最新电影列表
        @GET(ModuleName + "newmov")
        @JvmSuppressWildcards
        fun getNewMovAsync(): Deferred<BaseResponse<MutableList<MovieDTO>>>

        //获取首页热映电影列表
        @GET(ModuleName + "hotmov")
        @JvmSuppressWildcards
        fun gethotMovAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<MutableList<MovieDTO>>>


        //获取首页猜你喜欢列表
        @GET(ModuleName + "guess-like")
        @JvmSuppressWildcards
        fun getGuessLikeAsync(): Deferred<BaseResponse<MutableList<MovieDTO>>>

        //获取首页栏目分组列表
        @GET(ModuleName + "columns")
        @JvmSuppressWildcards
        fun getColumnsAsync(): Deferred<BaseResponse<MutableList<ColumnMovieDTO>>>


        companion object {

            const val ModuleName = "api/home/"
        }

    }
}
