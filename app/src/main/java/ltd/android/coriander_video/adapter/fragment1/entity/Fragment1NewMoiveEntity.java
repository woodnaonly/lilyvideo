package ltd.android.coriander_video.adapter.fragment1.entity;

import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase;
import ltd.android.coriander_video.dto.MovieDTO;

import java.util.List;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class Fragment1NewMoiveEntity extends Fragment1EntityBase {

    public List<MovieDTO> data;

    public Fragment1NewMoiveEntity(List<MovieDTO> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment1EntityBase.new_moive;
    }
}

