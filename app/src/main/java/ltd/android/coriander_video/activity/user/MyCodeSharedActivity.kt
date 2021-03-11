package ltd.android.coriander_video.activity.user

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_my_code_shared.*
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.utils.*
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper
import ltd.android.coriander_video.view_model.base.BaseViewModel
import java.io.FileOutputStream


class MyCodeSharedActivity : BaseActivity<BaseViewModel>() {
    val userInfo = UserPrefsHelper.getInstance().userInfo!!

    companion object {
        fun start(context: Context?) {
            val intent = Intent(context, MyCodeSharedActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE) //去掉标题栏，只去掉这一行不行，还有信息栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏，
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int {
        return ltd.android.coriander_video.R.layout.activity_my_code_shared
    }


    override fun initView() {
        super.initView()

//        val qrCode = QRCodeEncoder.syncEncodeQRCode("123213", 600)
        val qrCode =
            ZXingUtils.StringToBitMap(
                "123213",
                600,
                BitmapFactory.decodeResource(resources, ltd.android.coriander_video.R.mipmap.ic_launcher)
            )
        qr_i.setImageBitmap(qrCode)
        put_t.text = userInfo.myInviteCode

        copy_down_t.setOnClickListener {
            toastUtil.showToast("复制成功")
            copy_down_t.text = "复制成功"
            copy_down_t.setBackgroundResource(ltd.android.coriander_video.R.drawable.shape_bule)
            copy_down_t.setTextColor(ResourcesUtils.getColor(ltd.android.coriander_video.R.color.white))
            ClipboardManagerUtils.CopyToClipboard(it.context, userInfo.link.content)
        }

        code_back.setOnClickListener {
            finish()
        }

        screencap_b.setOnClickListener {
            val decorView = window.decorView
            decorView.isDrawingCacheEnabled = true
            decorView.buildDrawingCache()
            var drawingCache: Bitmap? = decorView.drawingCache
            if (drawingCache != null) {
                val file = FileManage.screenshot()
                val fileOutputStream = FileOutputStream(file)
                try {
                    drawingCache.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    toastUtil.showToast("保存成功")
                    screencap_b.setBackgroundResource(ltd.android.coriander_video.R.drawable.shape_bule)
                    screencap_b.setTextColor(ResourcesUtils.getColor(ltd.android.coriander_video.R.color.white))
                    screencap_b.text = "保存成功"
                    sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
                } catch (exception: Exception) {

                } finally {
                    CloseUtils.closeIO(fileOutputStream)
                    if (!drawingCache.isRecycled) {
                        drawingCache.recycle()
                    }
                    decorView.destroyDrawingCache()
                }


            }
        }
    }


}


