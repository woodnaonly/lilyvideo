package ltd.android.coriander_video.adapter.fragment1.entity;

import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase;
import ltd.android.coriander_video.entity.Ad;

import java.util.List;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class Fragment1BannerEntity extends Fragment1EntityBase {

    public List<Ad> data;

    public Fragment1BannerEntity(List<Ad> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment1EntityBase.banner;
    }
}

