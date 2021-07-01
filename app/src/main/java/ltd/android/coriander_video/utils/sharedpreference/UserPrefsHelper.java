package ltd.android.coriander_video.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import com.google.gson.Gson;
import ltd.android.coriander_video.app.App;
import ltd.android.coriander_video.dto.UserDTO;
import ltd.android.coriander_video.utils.GsonUtils;

import static ltd.android.coriander_video.utils.sharedpreference.SharedPreferenceKey.*;


public class UserPrefsHelper {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private UserDTO mUserDTO;


    private UserPrefsHelper() {
        sp = App.getAppContext().getSharedPreferences(PREFERENCE_USER_FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static UserPrefsHelper getInstance() {
        return UserPrefsHelper.Holder.INSTANCE;
    }

    public String getToken() {
        return sp.getString(KEY_TOKEN, null);
    }

    public Boolean isHasToken() {
        String token = getToken();
        return !TextUtils.isEmpty(token);
    }

    public void setToken(String token) {
        editor.putString(KEY_TOKEN, token).commit();

    }

    public void clearToken() {
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USER_INFO);
        editor.apply();
    }


    /**
     * 清除登录数据。
     */
    public void removeAll() {
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_USER_INFO);
        editor.apply();
    }

    /**
     * 查询存储的用户登录数据，如果不存在则返回null。
     */
    @Nullable
    public UserDTO getUserInfo() {
        if (mUserDTO == null) {
            Gson gson = GsonUtils.getInstance();
            String savedInfo = sp.getString(KEY_USER_INFO,"");
            if (TextUtils.isEmpty(savedInfo)) {
                return null;
            }
            return gson.fromJson(savedInfo, UserDTO.class);
        } else {
            return mUserDTO;
        }

    }

    public void clearUserInfo() {
        editor.remove(KEY_USER_INFO);
        editor.apply();
    }

    /**
     * 保存用户的登录数据。
     */
    public void setUserInfo(@NonNull UserDTO info) {
        mUserDTO = info;
        Gson gson = GsonUtils.getInstance();
        String infoToSave = gson.toJson(info);
        editor.putString(KEY_USER_INFO, infoToSave);
        editor.apply();
    }

    private static final class Holder {

        private static final UserPrefsHelper INSTANCE = new UserPrefsHelper();
    }

}
