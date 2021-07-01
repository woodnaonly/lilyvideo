package ltd.android.coriander_video.fragment.user.feed_back

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_feedback.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.user.feed_back.FeedBackTypeAdapter
import ltd.android.coriander_video.dto.FeedBackTypeDTO
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.net.http.api.FeedbackAPi
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class FeedBackFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.fragment_feedback;
    }

    companion object {
        fun newInstance(): FeedBackFragment {
            val fragment = FeedBackFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    val list = ArrayList<FeedBackTypeDTO>()
    private val mAdapter = FeedBackTypeAdapter(list)

    override fun initView() {
        super.initView()
        initList();
        recyclerview.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(activity, 3);
        recyclerview.adapter = mAdapter



        bt_submit.setOnClickListener {
            if (mAdapter.selectedPositin == -1) {
                toastUtil.showToast("请选择反馈的类型")
                return@setOnClickListener

            }
            if (et_feedback.text.length < 10) {
                toastUtil.showToast("反馈的内容不能少于10个字")
                return@setOnClickListener

            }

            val mapOf = mapOf(
                "content" to et_feedback.text.toString(),
                "type" to mAdapter.selectedPositin.toString()
            )
            launch {
                val response = withContext(Dispatchers.IO)
                {
                    FeedbackAPi.instance.feedbackSubmitAsync(mapOf).await()
                }
                if (response.success) {
                    toastUtil.showToast("提交成功")
                    mAdapter.selectedPositin = -1
                    mAdapter.notifyDataSetChanged()
                    et_feedback.text = null
                }

            }


        }


    }

    private fun initList() {

        list.add(FeedBackTypeDTO("无法播放"))
        list.add(FeedBackTypeDTO("播放卡顿"))
        list.add(FeedBackTypeDTO("标签错误"))
        list.add(FeedBackTypeDTO("分类错误"))
        list.add(FeedBackTypeDTO("搜索不准"))
        list.add(FeedBackTypeDTO("推荐不准"))
        list.add(FeedBackTypeDTO("无法下载"))
        list.add(FeedBackTypeDTO("其他"))

    }
}