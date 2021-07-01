package ltd.android.coriander_video.fragment.user

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.common_layout_recyclerview.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.user.UserNotificationAdapter
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.NoticeAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class UserNotificationFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.fragment_user_notification;
    }

    companion object {
        fun newInstance(): UserNotificationFragment {
            val fragment = UserNotificationFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()

        launch {

            val respone = withContext(IO) {
                NoticeAPi.instance.getNotifiListAsync()
            }.await()

            if (respone.success) {
                val userNotificationAdapter = UserNotificationAdapter(respone.data)
                recyclerView.adapter = userNotificationAdapter
                recyclerView.layoutManager =
                    androidx.recyclerview.widget.LinearLayoutManager(context)
            }


        }

    }
}