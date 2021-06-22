package ltd.android.coriander_video.fragment.user.feed_back

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.common_layout_recyclerview.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.user.feed_back.QuestionAdapter
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.FeedbackAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class QuestionFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.fragment_user_notification;
    }

    companion object {
        fun newInstance(): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()

        launch {

            val respone = withContext(IO) {
                FeedbackAPi.instance.getQuestionListAsync()
            }.await()

            if (respone.success) {
                val questionAdapter = QuestionAdapter(respone.data)
                recyclerView.adapter = questionAdapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            }


        }

    }
}