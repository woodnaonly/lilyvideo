package ltd.android.coriander_video.adapter.fragment2.entity;

import ltd.android.coriander_video.adapter.fragment2.entity.base.Fragment2EntityBase;
import ltd.android.coriander_video.dto.ColumnNavDTO;

/**
 * @author by 黄梦 on 2018/3/1.
 */
public class Fragment2MustSeeNavEntity extends Fragment2EntityBase {

    public ColumnNavDTO data;

    public Fragment2MustSeeNavEntity(ColumnNavDTO data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment2EntityBase.must_see_nav;
    }
}

