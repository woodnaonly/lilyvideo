package ltd.android.coriander_video.entity;


import java.io.Serializable;
import java.util.List;

public class Column implements Serializable {

    //columns START
    /**
     * id
     */
    public Integer id;
    /**
     * cls
     */
    public Integer cls;
    /**
     * mod_name
     */
    public String modName;
    /**
     * thumbnail
     */
    public String thumbnail;

    public List<ColumnNav> subclass;


}

