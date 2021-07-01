package ltd.android.coriander_video.net.http.interceptor;


import androidx.annotation.NonNull;
import ltd.android.coriander_video.app.App;
import ltd.android.coriander_video.utils.NetWorkUtil;
import ltd.android.coriander_video.utils.sharedpreference.UserPrefsHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 处理HTTP公共参数
 */

public class TokenInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        UserPrefsHelper userPrefsHelper = UserPrefsHelper.getInstance();
        Request request;
        if (userPrefsHelper.isHasToken()) {
            request = chain.request().newBuilder().addHeader("token", userPrefsHelper.getToken()).build();
        } else {
            request = chain.request();
        }



        Response originalResponse = chain.proceed(request);
        if (NetWorkUtil.isNetConnected(App.getAppContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma").build();
        }
    }
}
