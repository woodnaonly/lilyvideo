package ltd.android.coriander_video.utils

import android.Manifest
import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

/**
 *
 *
 * @author by 梁馨 on 2019/3/13.
 */
fun hasCameraPermission(context: Context): Boolean {
    return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA)
}