package ltd.android.coriander_video.dto;


import ltd.android.coriander_video.entity.Tag;

import java.io.Serializable;
import java.util.List;

public class TagDTO implements Serializable {

    private static final long serialVersionUID = -4934742483534862938L;

    private Integer pid;
    private String pname;
    private List<Tag> subclass;
    //是否焦点
    public Boolean isChecked = false;
    //是否有子view 被选择
    public Boolean isSelected = false;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<Tag> getSubclass() {
        return subclass;
    }

    public void setSubclass(List<Tag> subclass) {
        this.subclass = subclass;
    }
}
