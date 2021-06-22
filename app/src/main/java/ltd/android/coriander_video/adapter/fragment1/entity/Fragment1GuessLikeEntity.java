package ltd.android.coriander_video.adapter.fragment1.entity;

import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase;
import ltd.android.coriander_video.dto.MovieDTO;

import java.util.List;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment1GuessLikeEntity extends Fragment1EntityBase {

    public List<MovieDTO> data;

    public Fragment1GuessLikeEntity(List<MovieDTO> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment1EntityBase.guess_like;
    }
}

