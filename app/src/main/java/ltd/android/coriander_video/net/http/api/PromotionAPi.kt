package ltd.android.coriander_video.net.http.api

import kotlinx.coroutines.Deferred
import ltd.android.coriander_video.dto.promotion.PromotionBase
import ltd.android.coriander_video.net.http.ApiBuild
import ltd.android.coriander_video.net.http.response.base.BaseResponse
import retrofit2.http.GET

@Suppress("unused")
object PromotionAPi {
    val instance: Service by lazy { ApiBuild.createApi(Service::class.java) }


    interface Service {
        /**
         * 获取推广链接
         */
        @GET(ModuleName + "link")
        fun getLinkAsync(): Deferred<BaseResponse<PromotionBase>>


        /**
         * 获取交流群链接
         */
        @GET(ModuleName + "group")
        fun getGroupAsync(): Deferred<BaseResponse<PromotionBase>>


        /**
         * 获取推广页面背景图
         */
        @GET(ModuleName + "share")
        fun getShareAsync(): Deferred<BaseResponse<PromotionBase>>

        companion object {
            const val ModuleName = "api/promotion/"
        }

    }
}
