package ltd.android.coriander_video.dto;

import java.io.Serializable;

/**
 * @author by 梁馨 on 2019/3/13.
 */
public class FeedBackTypeDTO implements Serializable {

    public FeedBackTypeDTO(String typeName) {
        this.typeName = typeName;
    }

    public String typeName;
}
