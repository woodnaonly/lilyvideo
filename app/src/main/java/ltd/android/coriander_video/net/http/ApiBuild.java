package ltd.android.coriander_video.net.http;


import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import ltd.android.coriander_video.app.App;
import ltd.android.coriander_video.cofigure.AppConfigure;
import ltd.android.coriander_video.utils.GsonUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiBuild {

    private final long TIME_UOT = AppConfigure.INSTANCE.getTIME_UOT();
    private final int CACHE_SIZE = 1024 * 1024 * 100;
    private final OkHttpClient okHttpClient;
    private Retrofit retrofit;

    //构造方法私有
    private ApiBuild() {
        File cacheFile = new File(App.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, CACHE_SIZE); //100Mb

        okHttpClient = new OkHttpClient.Builder().readTimeout(TIME_UOT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_UOT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_UOT, TimeUnit.MILLISECONDS)
//                .addInterceptor(new TokenInterceptor())
                .cache(cache).build();

        Gson gson = GsonUtils.getInstance();
        retrofit = new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(AppConfigure.INSTANCE.getBASE_URL())
                .build();
    }

    public static ApiBuild getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static <T> T createApi(Class<T> serviceClass) {
        return getInstance().retrofit.create(serviceClass);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private static class SingletonHolder {

        private static final ApiBuild INSTANCE = new ApiBuild();
    }


}