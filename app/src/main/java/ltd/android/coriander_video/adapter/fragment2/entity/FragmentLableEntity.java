package ltd.android.coriander_video.adapter.fragment2.entity;

import ltd.android.coriander_video.adapter.fragment2.entity.base.Fragment2EntityBase;

/**
 * @author by 梁馨 on 2018/3/1.
 */
public class FragmentLableEntity extends Fragment2EntityBase {

    public String data;

    public FragmentLableEntity(String data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return Fragment2EntityBase.label;
    }
}

