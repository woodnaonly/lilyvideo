package ltd.android.coriander_video.utils;

/**
 * @author by 梁馨 on 2019/2/26.
 */
public class StringUtils {

    public static String trim(String string) {
        return string.trim();
    }

    /**
     * 去掉字符串里面的html代码。<br>
     * 要求数据要规范，比如大于小于号要配套,否则会被集体误杀。
     *
     * @param content 内容
     * @return 去掉后的内容
     */

    public static String stripHtml(String content) {
        // <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
        // <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
        // 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
        // 还原HTML
        // content = HTMLDecoder.decode(content);
        //&ldquo;&quot;&nbsp;
        content = content.replaceAll("&.dquo;", "\"");
        content = content.replaceAll("&nbsp;", " ");
        return content;
    }
}
