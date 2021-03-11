package ltd.android.coriander_video.dto;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import ltd.android.coriander_video.utils.FormatUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MovieDTO implements Serializable {

    public Integer dislikeCnt;

    public String createDate;

    public long lastTime;

    public long UserId;


    @Id(assignable = true)
    private Long id;
    /**
     * mov_name
     */
    private String movName;
    /**
     * mov_desc
     */
    private String movDesc;
    /**
     * mov_score
     */
    private Float movScore;
    /**
     * play_count
     */
    private Integer playCount;

    private Integer loveCnt;

    private String cover;

    private Integer movCls;

    private String file;

    private Integer loveStatus = 0;

    private Integer isFav = 0;
    @Transient
    private List<RelTagDTO> relTagName = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getMovDesc() {
        return movDesc;
    }

    public void setMovDesc(String movDesc) {
        this.movDesc = movDesc;
    }

    public Float getMovScore() {
        return movScore;
    }

    public void setMovScore(Float movScore) {
        this.movScore = movScore;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public String getPlayCountFormat() {
        return FormatUtils.INSTANCE.formatNum(playCount, false) + "次播放";
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getLoveCnt() {
        return loveCnt;
    }

    public void setLoveCnt(Integer loveCnt) {
        this.loveCnt = loveCnt;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getMovCls() {
        return movCls;
    }

    public void setMovCls(Integer movCls) {
        this.movCls = movCls;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getLoveStatus() {
        return loveStatus;
    }

    public void setLoveStatus(Integer loveStatus) {
        this.loveStatus = loveStatus;
    }

    public Integer getIsFav() {
        return isFav;
    }

    public void setIsFav(Integer isFav) {
        this.isFav = isFav;
    }

    public List<RelTagDTO> getRelTagName() {

        return relTagName;
    }

    public Boolean isFav() {
        if (isFav == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setRelTagName(List<RelTagDTO> relTagName) {
        this.relTagName = relTagName;
    }
}
