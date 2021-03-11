package ltd.android.coriander_video.adapter.fragment1.entity.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class Fragment1EntityBase implements MultiItemEntity {


    public static final int banner = 1;
    public static final int movie_class = 2;
    public static final int new_moive = 3;
    public static final int hot_moive = 4;
    public static final int guess_like = 5;
    public static final int home_columns = 6;
    public static final int divide_line = 7;

    protected int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}

