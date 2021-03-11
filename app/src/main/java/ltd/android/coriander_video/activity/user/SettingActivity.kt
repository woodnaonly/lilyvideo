package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_setting.*
import ltd.android.coriander_video.BuildConfig
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.utils.FileManage
import ltd.android.coriander_video.utils.FileUtils
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel


class SettingActivity : BaseActivity<BaseViewModel>() {


    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, SettingActivity::class.java)
            context?.startActivity(intent)
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_setting
    }


    override fun initView() {
        super.initView()

        setting_usernameger_l.setOnClickListener {
            val userInfo = UserPrefsHelper.getInstance().userInfo!!
            if (userInfo.isVisit) {
                LoginActivity.start(this)
            } else {
                UserManagerActivity.start(this)
            }
        }
        setting_version_t.text = "当前版本${BuildConfig.VERSION_NAME}"

        setting_size_pg_t.text = FileManage.GlideCacheSize()


        setting_xieyi_l.setOnClickListener {
            XieYiActivity.start(this)
        }

        setting_back.setOnClickListener {
            finish()
        }
        setting_clear_l.setOnClickListener {
            showExitLoginDialog()
        }
    }
    val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    // ================================ 生成不同类型的对话框
    private fun showExitLoginDialog() {
        QMUIDialog.MessageDialogBuilder(this)
            .setMessage("是否清理当前App缓存？")
            .addAction("取消") { dialog, index ->
                dialog.dismiss()
            }
            .addAction("确定") { dialog, index ->
               val glideCache = FileManage.GlideCache()
                FileUtils.deleteFilesInDir(glideCache)
                setting_size_pg_t.text = FileManage.GlideCacheSize()
                dialog.dismiss()
            }
            .create(mCurrentDialogStyle).show()
    }
}


