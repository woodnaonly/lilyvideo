package ltd.android.coriander_video.net.http.response.base;

import com.google.gson.annotations.SerializedName;

/**
 * @author by 梁馨 on 2019/2/26.
 */
public class BaseResponse<T> {
    @SerializedName("code")
    public int code;
    @SerializedName("data")

    public T data;
    @SerializedName("description")
    public String description;

    @SerializedName("enumCode")
    public String enumCode;
    @SerializedName("msg")

    public String msg;
    @SerializedName("success")
    public boolean success;

    @SerializedName("pageNum")
    public int pageNum;

    @SerializedName("pageSize")
    public int pageSize;

    @SerializedName("pages")
    public int pages;

    @SerializedName("total")
    public int total;

    public Boolean isRefresh = false;


    public static class Code {
        public static int successCode = 0;
        public static int invalidTokenCode = 401;

    }
}
