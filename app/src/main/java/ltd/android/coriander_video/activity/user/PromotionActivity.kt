package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.text.Html
import kotlinx.android.synthetic.main.activity_promotion.*
import kotlinx.android.synthetic.main.item_task_layout_innovation.*
import kotlinx.android.synthetic.main.item_task_layout_qr.*
import kotlinx.android.synthetic.main.item_task_layout_register.*
import kotlinx.android.synthetic.main.item_task_layout_register_phone.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel

/**
 * 推广
 */
class PromotionActivity : BaseActivity<BaseViewModel>() {

    val userPrefsHelper = UserPrefsHelper.getInstance()
    val userDTO = userPrefsHelper.userInfo!!

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, PromotionActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_promotion
    }


    override fun initView() {
        super.initView()
        promotion_my_t.setOnClickListener {
            PurPromotionActivity.start(it.context)
        }


        promotion_back.setOnClickListener {
            finish()
        }


        GlideUtils.loadImg(setting_size_pg_t, userDTO.header)
        promot_name_t.text = userDTO.getName()
        promot_move_t1.text = userDTO.getDailyViewNum()
        promot_move_t2.text = userDTO.getLeftViewNum()
        promot_code_t.text = "我的邀请码为：${userDTO.myInviteCode}"
        promot_next_level_t.text = "距离下一等级还差${userDTO.nextLevelNeed}人"


        val level = userDTO.level
        promot_level_i1.setImageResource(userDTO.getLevelResource(level))
        promot_level_t1.text = userDTO.getLevelExplain(level)

        val levelNext = level + 1
        promot_level_i2.setImageResource(userDTO.getLevelResource(levelNext))
        promot_level_t2.text = userDTO.getLevelExplain(levelNext)

        tv_sub_register.text = Html.fromHtml(
            String.format(
                "用户名注册，每日观影次数<font color=\"#dcb182\">+%1\$d</font> <br/> 用户名注册并绑定手机号，每日观影次数<font color=\"#dcb182\">+%2\$d</font>",
                2, 4
            )
        )

        tv_sub_register_phone.text = Html.fromHtml(
            String.format(
                "手机号注册，每日观影次数<font color=\"#dcb182\">+%1\$d</font>，(*为防止APP被封丢失您的推广信息，可通过注册账号绑定推广人数，强烈建议注册*)",
                4
            )
        )

        tv_sub_innovation.text = Html.fromHtml(
            String.format(
                "注册并填写好友邀请码，每日观影次数<font color=\"#dcb182\">+%1\$d</font>",
                2
            )
        )

        tv_sub_qr.text = Html.fromHtml(
            String.format(
                "保存推广二维码，每日观影次数<font color=\"#dcb182\">+%1\$d</font>",
                2
            )
        )

        promot_code_i.setOnClickListener {
            MyCodeSharedActivity.start(this)
        }

        promot_put_t.setOnClickListener {
            promot_code_i.performClick()
        }

//        tv_sub_register.text = String.format(
//            "用户名注册，每日观影次数<font color=\"#dcb182\">+%1$d</font> <br/> 用户名注册并绑定手机号，每日观影次数<font color=\"#dcb182\">+%2\$d</font>"
//            , 2, 4
//        )
    }


}


