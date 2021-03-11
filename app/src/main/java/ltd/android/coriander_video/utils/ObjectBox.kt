package ltd.android.coriander_video.utils

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import ltd.android.coriander_video.BuildConfig
import ltd.android.coriander_video.dto.MyObjectBox

/**
 * Singleton to keep BoxStore reference.
 */
object ObjectBox {

    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

        if (BuildConfig.DEBUG) {
            Log.d("coriander_video", "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
        }
    }

}