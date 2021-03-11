package ltd.android.coriander_video.net.http.request;

import com.google.gson.annotations.Expose;
import ltd.android.coriander_video.cofigure.AppConfigure;

import java.util.List;

/**
 * @author by 梁馨 on 2019/3/5.
 */
public class LabelSelectRequest {
    public List tagIds;
    public int page;
    public int pageSize= AppConfigure.INSTANCE.getGlobalPageSize();
    @Expose()
    public Boolean isRefresh = false;
}
