package ltd.android.coriander_video.adapter.fragment1.entity;

import ltd.android.coriander_video.adapter.fragment1.entity.base.Fragment1EntityBase;
import ltd.android.coriander_video.dto.ColumnMovieDTO;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment1ColumnsMoiveEntity extends Fragment1EntityBase {

    public ColumnMovieDTO data;

    public Fragment1ColumnsMoiveEntity(ColumnMovieDTO data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment1EntityBase.home_columns;
    }
}

