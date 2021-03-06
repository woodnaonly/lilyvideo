package ltd.android.coriander_video.activity.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.core.content.ContextCompat
import android.text.InputType
import android.util.Log
import android.view.View
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import com.zhihu.matisse.listener.OnCheckedListener
import kotlinx.android.synthetic.main.activity_user_manager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.dto.UserDTO
import ltd.android.coriander_video.event.LoginEvent
import ltd.android.coriander_video.net.http.api.AuthAPi
import ltd.android.coriander_video.net.http.api.MemberAPi
import ltd.android.coriander_video.net.http.apiImp.MemberImp
import ltd.android.coriander_video.utils.*
import ltd.android.coriander_video.utils.glide.GlideUtils
import ltd.android.coriander_video.utils.matisse.GifSizeFilter
import ltd.android.coriander_video.utils.matisse.Glide4Engine
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


class UserManagerActivity : BaseActivity<BaseViewModel>() {

    val userPrefsHelper = UserPrefsHelper.getInstance()
    lateinit var userInfo: UserDTO

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, UserManagerActivity::class.java)
            context?.startActivity(intent)
        }

        private const val REQUEST_CODE_CHOOSE = 1023
    }


    override fun layoutId(): Int {
        return R.layout.activity_user_manager
    }

    override fun isApplyEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(loginEvent: LoginEvent) {
        initView()
    }


    override fun initView() {
        super.initView()
        userInfo = userPrefsHelper.userInfo!!
        GlideUtils.loadImg(civ_head, userInfo.header)
        tv_modify_phone.text = userInfo.phone
        tv_modify_nick_name.text = userInfo.name
        when (userInfo.gender) {
            UserDTO.genderEnum.bot -> {
                view_sex.visibility = View.GONE
                tv_modify_sex.text = "???"
            }
            UserDTO.genderEnum.girl -> {
                view_sex.visibility = View.GONE
                tv_modify_sex.text = "???"
            }
            UserDTO.genderEnum.unknown -> {
                view_sex.visibility = View.VISIBLE
                tv_modify_sex.text = "?????????"
            }
        }
        iv_back.setOnClickListener {
            finish()
        }
        ll_modify_head.setOnClickListener {
            val tempFile = FileManage.hearCrop()
            val uri = Uri.fromFile(tempFile)
            val options = UCrop.Options()
            options.useSourceImageAspectRatio()
            options.setCircleDimmedLayer(true)//??????????????????????????????
            options.setShowCropGrid(false)  //??????????????????????????????
            options.setShowCropFrame(false) //??????????????????????????????(true???????????????)
            options.setAllowedGestures(
                UCropActivity.SCALE, UCropActivity.SCALE,
                UCropActivity.SCALE
            )
            options.setToolbarColor(
                ContextCompat.getColor(this, R.color.dracula_primary)
            )
            options.setStatusBarColor(
                ContextCompat.getColor(this, R.color.dracula_primary)
            )
//            options.setToolbarTitle("??????")
            options.setHideBottomControls(true) //?????????????????????
            options.setFreeStyleCropEnabled(false)//??????????????????????????????
            Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .crop(true)
                .cropOptions(options)
                .cropUri(uri)
                .captureStrategy(
                    CaptureStrategy(
                        true, ResourcesUtils.getString(R.string.providerName)
                    )
                )
                .maxSelectable(9)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(DensityUtil.dip2px(this, 120f))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(Glide4Engine())    // for glide-V4
                .setOnSelectedListener({ _uriList, _pathList -> })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(OnCheckedListener { })
                .forResult(REQUEST_CODE_CHOOSE)
        }


        ll_modify_nickname.setOnClickListener {
            showNickNameDialog()
        }
        ll_modify_sex.setOnClickListener {
            if (userInfo.gender == UserDTO.genderEnum.unknown)
                showSexDialog()
        }
        bt_logout.setOnClickListener {
            showExitLoginDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            val pathResult = Matisse.obtainPathResult(data)
            if (pathResult.isNotEmpty()) {
                val path = pathResult[0]
                val file = File(path)
                val builder = MultipartBody.Builder()
                builder.setType(MultipartBody.FORM)
                val body = RequestBody.create(MediaType.parse("image/*"), file)
                builder.addFormDataPart("file", file.name, body)
                launch {
                    val response = withContext(Dispatchers.IO)
                    { MemberAPi.instance.updateHeaderAsync(builder.build()).await() }
                    if (response.success) {
                        MemberImp.getUserInfo {
                        }

                    } else {
                        toastUtil.showToast(response.msg)
                    }
                }
            }



            Log.e("OnActivityResult ", Matisse.obtainOriginalState(data).toString())
            Log.e("OnActivityResult ", pathResult.toString())
        }
    }

    val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog
    private fun showNickNameDialog() {
        val builder = QMUIDialog.EditTextDialogBuilder(this)
        builder.setTitle("??????")
            .setPlaceholder("????????????????????????")
            .setInputType(InputType.TYPE_CLASS_TEXT)
            .addAction("??????") { dialog, index -> dialog.dismiss() }
            .addAction("??????") { dialog, index ->
                val text = builder.editText.text.toString()
                if (text.isNotEmpty()) {
                    dialog.dismiss()
                    launch {
                        val response = withContext(Dispatchers.IO)
                        {
                            val mapOf = mapOf("name" to text)
                            MemberAPi.instance.nameUpdateAsync(mapOf).await()
                        }
                        if (response.success) {
                            MemberImp.getUserInfo {
                            }
                        } else {
                            toastUtil.showToast(response.msg)
                        }
                    }

                } else {
                    toastUtil.showToast("??????????????????!")
                }
            }
            .create(mCurrentDialogStyle).show()
    }


    private fun showSexDialog() {

        val items = arrayOf("???", "???")
        QMUIDialog.MenuDialogBuilder(this)
            .setTitle("???????????????,?????????????????????")
            .addItems(items) { dialog, which ->
                launch {
                    val response = withContext(Dispatchers.IO)
                    {
                        val mapOf = mapOf("name" to which.toString())
                        MemberAPi.instance.genderUpdateAsync(mapOf).await()
                    }
                    if (response.success) {
                        MemberImp.getUserInfo {
                        }
                    } else {
                        toastUtil.showToast(response.msg)
                    }
                }
                dialog.dismiss()
            }
            .create(mCurrentDialogStyle).show()
    }

    // ================================ ??????????????????????????????
    private fun showExitLoginDialog() {
        QMUIDialog.MessageDialogBuilder(this)
            .setMessage("????????????????????????")
            .addAction("??????") { dialog, index ->
                dialog.dismiss()
            }
            .addAction("??????") { dialog, index ->
                launch {
                    val visitorResponse = withContext(Dispatchers.IO)
                    {
                        val uuid = UUIDS.getUUID()
                        val sign = SignUtils.Sign(uuid)
                        val map = mapOf(
                            "key" to uuid,
                            "sign" to sign
                        )
                        AuthAPi.instance.getVisitorAsync(map).await()
                    }
                    if (visitorResponse.success) {
                        userPrefsHelper.token = visitorResponse.data
                        MemberImp.getUserInfo {
                            finish()
                        }
                    }
                }

                dialog.dismiss()
            }
            .create(mCurrentDialogStyle).show()
    }
}


