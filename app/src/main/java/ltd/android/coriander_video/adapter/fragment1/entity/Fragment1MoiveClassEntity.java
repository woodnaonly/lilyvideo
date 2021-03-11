package ltd.android.coriander_video.adapter.fragment1.entity;

import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase;
import ltd.android.coriander_video.entity.MovieClass;

import java.util.List;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class Fragment1MoiveClassEntity extends Fragment1EntityBase {

    public List<MovieClass> data;

    public Fragment1MoiveClassEntity(List<MovieClass> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment1EntityBase.movie_class;
    }
}

