package ltd.android.coriander_video.adapter.fragment2.entity.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment2EntityBase implements MultiItemEntity {


    public static final int hot_nav = 1;
    public static final int hot_star = 2;
    public static final int must_see_nav = 3;
    public static final int label = 4;

    protected int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}

