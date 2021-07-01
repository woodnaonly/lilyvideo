package ltd.android.coriander_video.fragment.history

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import io.objectbox.Box
import kotlinx.android.synthetic.main.common_layout_recyclerview.*
import kotlinx.android.synthetic.main.fragment_history.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.adapter.history.HistoryAdapter
import ltd.android.coriander_video.dto.MovieDTO
import ltd.android.coriander_video.dto.MovieDTO_
import ltd.android.coriander_video.fragment.base.BaseFragment
import ltd.android.coriander_video.utils.CalendarUtils
import ltd.android.coriander_video.utils.CalendarUtils.getDayStartAndEndTime
import ltd.android.coriander_video.utils.GsonUtils
import ltd.android.coriander_video.utils.ObjectBox
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 *
 *
 * @author by 黄梦 on 2019/2/28.
 */
class HistoryFragment : BaseFragment<BaseViewModel>() {
    override fun layoutId(): Int {
        return R.layout.fragment_history;
    }

    companion object {
        private const val dataName = "data"

        fun newInstance(positon: Int): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putInt(dataName, positon)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
        super.initView()
        val arguments = arguments

        var list: List<MovieDTO> = ArrayList<MovieDTO>()
        val position = arguments?.getInt(dataName, 0)

        when (position) {
            0 -> {
                val time = getDayStartAndEndTime()
                val mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor(MovieDTO::class.java)
                val query = mBox.query().orderDesc(MovieDTO_.lastTime)
                    .between(MovieDTO_.lastTime, time[0], time[1]).build()
                list = query.find()
            }
            1 -> {
                val time = CalendarUtils.getDaySevenRange()
                val mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor(MovieDTO::class.java)
                val query = mBox.query().orderDesc(MovieDTO_.lastTime)
                    .between(MovieDTO_.lastTime, time[0], time[1]).build()
                list = query.find()
            }
            2 -> {
                val time = CalendarUtils.getDaySevenRange()
                val mBox: Box<MovieDTO> = ObjectBox.boxStore.boxFor(MovieDTO::class.java)

                val query = mBox.query().orderDesc(MovieDTO_.lastTime)
                    .less(MovieDTO_.lastTime, time[0] - 1).build()
                list = query.find()
            }
        }


        list.forEach {
            Log.d("time", GsonUtils.GsonString(it))
        }
        val adapter = HistoryAdapter(list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context)

        if (list.isEmpty()) {
            search_empty_view.visibility = View.VISIBLE
        }
        refreshLayout.isEnableLoadMore = false
        refreshLayout.isEnableRefresh = false


    }
}