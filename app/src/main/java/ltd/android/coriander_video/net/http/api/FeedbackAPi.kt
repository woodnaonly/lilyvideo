package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.NoticeDTO
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

@Suppress("unused")
object FeedbackAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {

        @GET(ModuleName + "faq/list")
        fun getQuestionListAsync(): Deferred<BaseResponse<List<NoticeDTO>>>

        @FormUrlEncoded
        @POST(ModuleName + "feedback/list")
        fun feedbackUpdateAsync(): Deferred<BaseResponse<String>>

        @FormUrlEncoded
        @POST(ModuleName + "feedback/submit")
        fun feedbackSubmitAsync(@FieldMap map: Map<String, String>): Deferred<BaseResponse<Any>>

        companion object {
            const val ModuleName = "api/"
        }

    }
}
