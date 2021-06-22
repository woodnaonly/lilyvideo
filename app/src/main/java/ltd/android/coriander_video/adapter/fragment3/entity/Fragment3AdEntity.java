package ltd.android.coriander_video.adapter.fragment3.entity;

import ltd.android.coriander_video.adapter.fragment3.entity.base.Fragment3EntityBase;
import ltd.android.coriander_video.entity.Ad;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment3AdEntity extends Fragment3EntityBase {

    public Ad data;

    public Fragment3AdEntity(Ad data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment3EntityBase.item_ad;
    }
}

