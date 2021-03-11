package ltd.android.coriander_video.adapter.user

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ltd.android.coriander_video.R
import ltd.android.coriander_video.dto.UserDTO

/**
 * @author by 梁馨 on 2018/4/24.
 */
class PurPromotionAdapter(val DataList: List<UserDTO>) :
    BaseQuickAdapter<UserDTO, BaseViewHolder>(
        R.layout.card_put_promo,
        DataList
    ) {

    override fun convert(helper: BaseViewHolder, item: UserDTO) {
        helper.setText(R.id.card_put_promo_t1, item.getName())
        helper.setText(R.id.card_put_promo_t2, item.phone)
        helper.setText(R.id.card_put_promo_t3, item.registerTime)
    }

}
