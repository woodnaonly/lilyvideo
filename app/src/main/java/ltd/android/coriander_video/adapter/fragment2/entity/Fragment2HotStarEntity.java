package ltd.android.coriander_video.adapter.fragment2.entity;

import ltd.android.coriander_video.adapter.fragment2.entity.base.Fragment2EntityBase;
import ltd.android.coriander_video.dto.ActorMovieDTO;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment2HotStarEntity extends Fragment2EntityBase {

    public ActorMovieDTO data;

    public Fragment2HotStarEntity(ActorMovieDTO data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment2EntityBase.hot_star;
    }
}

