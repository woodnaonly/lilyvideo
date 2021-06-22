package ltd.android.coriander_video.utils

import android.content.Context
import android.text.ClipboardManager

/**
 *
 *
 * @author by 黄梦 on 2019/3/21.
 */
object ClipboardManagerUtils
{
    fun CopyToClipboard(context: Context, text: String) {
        val clip = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //clip.getText(); // 粘贴
        clip.text = text // 复制
    }
}