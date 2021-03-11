package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.UserDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

object MemberAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {
        @JvmSuppressWildcards
        @GET(ModuleName + "info")
        fun getUserInfoAsync(): Deferred<BaseResponse<UserDTO>>


        @JvmSuppressWildcards
        @POST(ModuleName + "header/update")
        fun updateHeaderAsync(@Body requestBody: RequestBody): Deferred<BaseResponse<Any>>


        @FormUrlEncoded
        @JvmSuppressWildcards
        @POST(ModuleName + "use-vip-code")
        fun useVipCodeAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<Any>>


        @FormUrlEncoded
        @JvmSuppressWildcards
        @POST(ModuleName + "name/update")
        fun nameUpdateAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<Any>>

        @FormUrlEncoded
        @JvmSuppressWildcards
        @POST(ModuleName + "gender/update")
        fun genderUpdateAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<Any>>


        @JvmSuppressWildcards
        @GET(ModuleName + "invites")
        fun getInvitesAsync(): Deferred<BaseResponse<List<UserDTO>>>


        companion object {
            const val ModuleName = "api/member/"
        }

    }
}
