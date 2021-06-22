package ltd.android.coriander_video.net.http.apiImp

import kotlinx.coroutines.*
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.net.http.api.PromotionAPi
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import org.greenrobot.eventbus.EventBus

/**
 * @author by 黄梦 on 2019/3/27.
 */
object MemberImp {
    fun getUserInfo(block: suspend CoroutineScope.() -> Unit) {
        GlobalScope.launch {
            /**
             * 获取用户信息
             */

            val userInfoResponseDeferred = withContext(Dispatchers.IO)
            {
                MemberAPi.instance.getUserInfoAsync()
            }

            /**
             * 推广链接
             */
            val linkDeferred = withContext(Dispatchers.IO)
            {
                PromotionAPi.instance.getLinkAsync()
            }

            /**
             * 分享链接
             */
            val getGroupDeferred = withContext(Dispatchers.IO)
            {
                PromotionAPi.instance.getGroupAsync()
            }

            val userInfoResponse = userInfoResponseDeferred.await()
            val linkResponse = linkDeferred.await()
            val groupResponse = getGroupDeferred.await()

            if (userInfoResponse.success &&
                linkResponse.success &&
                groupResponse.success
            ) {
                val userDTO = userInfoResponse.data
                userDTO.link = linkResponse.data
                userDTO.group = groupResponse.data
                val userPrefsHelper = UserPrefsHelper.getInstance()
                userPrefsHelper.setUserInfo(userDTO)
                try {
                    block()
                } catch (exception: Exception) {
                }
                EventBus.getDefault().post(LoginEvent())
            }
        }
    }
}



