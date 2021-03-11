package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.ActorMovieDTO
import ltd.android.coriander_video.dto.Categorie
import ltd.android.coriander_video.dto.ColumnNavDTO
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Suppress("unused")
object ZhuanLanAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {


        //专栏
        @GET(ModuleName + "column/navs")
        @JvmSuppressWildcards
        fun getZhuanTiAsync(): Deferred<BaseResponse<List<ColumnNavDTO>>>


        //专栏
        @GET(ModuleName + "column/movies")
        @JvmSuppressWildcards
        fun getcolumnMoviesAsync(@QueryMap map: Map<String, Any>): Deferred<BaseResponse<List<MovieDTO>>>


        //人气明星
        @GET(ModuleName + "actor/hots")
        @JvmSuppressWildcards
        fun getActorHotsAsync(): Deferred<BaseResponse<List<ActorMovieDTO>>>

        //人气明星
        @GET(ModuleName + "actor/categories")
        @JvmSuppressWildcards
        fun getActorCategoriesAsync(): Deferred<BaseResponse<MutableList<Categorie>>>


        companion object {

            const val ModuleName = "api/"
        }

    }
}
