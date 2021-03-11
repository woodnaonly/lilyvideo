package ltd.android.coriander_video.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Vibrator
import android.text.TextUtils
import cn.bingoogolapple.qrcode.core.QRCodeView
import kotlinx.android.synthetic.main.activity_scan.*
import ltd.android.coriander_video.R
import ltd.android.coriander_video.activity.base.BaseActivity
import ltd.android.coriander_video.utils.hasCameraPermission
import ltd.android.coriander_video.view_model.base.BaseViewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class ScanActivity : BaseActivity<BaseViewModel>(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks, QRCodeView.Delegate {
    override fun onScanQRCodeSuccess(result: String?) {
        val empty = TextUtils.isEmpty(result)
        if (!empty) {
            vibrate()
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            val uri = Uri.parse(result)
            intent.data = uri
            startActivity(intent)
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {
    }


    private companion object {
        const val RC_CAMERA_PERM = 123
    }


    override fun layoutId(): Int {
        return ltd.android.coriander_video.R.layout.activity_scan
    }


    @AfterPermissionGranted(RC_CAMERA_PERM)
    fun cameraTask() {
        if (hasCameraPermission(this@ScanActivity)) {
            startCamera()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_camera),
                RC_CAMERA_PERM,
                Manifest.permission.CAMERA
            )
        }
    }

    private fun startCamera() {
        mZXingView.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect() // 显示扫描框，并开始识别
    }


    override fun initView() {
        super.initView()
        cameraTask()
        mZXingView.setDelegate(this)
    }

    override fun onStart() {
        super.onStart()
        startCamera()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        mZXingView.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        mZXingView.onDestroy() // 销毁二维码扫描控件
        super.onDestroy()
    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(200)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    override fun onRationaleDenied(requestCode: Int) {
    }

    override fun onRationaleAccepted(requestCode: Int) {
    }


}


