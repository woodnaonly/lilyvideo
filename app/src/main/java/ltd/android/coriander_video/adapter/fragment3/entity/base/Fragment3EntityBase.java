package ltd.android.coriander_video.adapter.fragment3.entity.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment3EntityBase implements MultiItemEntity {


    public static final int item_video = 1;
    public static final int item_ad = 2;

    protected int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}

