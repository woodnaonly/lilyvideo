package ltd.android.coriander_video.adapter.user

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.user.PlacardInfoActivity
import ltd.android.coriander_video.dto.NoticeDTO
import ltd.android.coriander_video.utils.StringUtils

/**
 * @author by 黄梦 on 2018/4/24.
 */
class UserNotificationAdapter(val DataList: List<NoticeDTO>) :
    BaseQuickAdapter<NoticeDTO, BaseViewHolder>(
        R.layout.item_user_notification_notice,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: NoticeDTO) {

        helper.setText(R.id.text_notification_title, item.title)
        helper.setText(R.id.text_notificaiton_date, item.updateTime.substring(0, 7))
        helper.setText(R.id.text_notification_content, StringUtils.stripHtml(item.content))


        helper.itemView.setOnClickListener {
            PlacardInfoActivity.start(item, it.context)
        }

    }

}
