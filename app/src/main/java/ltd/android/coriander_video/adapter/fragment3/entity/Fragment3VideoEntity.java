package ltd.android.coriander_video.adapter.fragment3.entity;

import ltd.android.coriander_video.adapter.fragment3.entity.base.Fragment3EntityBase;
import ltd.android.coriander_video.dto.MovieDTO;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class Fragment3VideoEntity extends Fragment3EntityBase {

    public MovieDTO data;

    public Fragment3VideoEntity(MovieDTO data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment3EntityBase.item_video;
    }
}

