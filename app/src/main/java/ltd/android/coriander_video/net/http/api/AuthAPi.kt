package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

@Suppress("unused")
object AuthAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {


        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "visitor")
        fun getVisitorAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<String>>

        @JvmSuppressWildcards
        @GET(ModuleName + "refresh")
        fun refreshTokenAsync(): Deferred<BaseResponse<Any>>


        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "signin")
        fun signinAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<String>>

        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "reg-msg")
        fun regMsgAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<String>>


        @JvmSuppressWildcards
        @FormUrlEncoded
        @POST(ModuleName + "register")
        fun registerAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<String>>

        companion object {
            const val ModuleName = "api/auth/"
        }

    }
}
