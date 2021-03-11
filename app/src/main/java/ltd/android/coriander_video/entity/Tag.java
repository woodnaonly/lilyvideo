package ltd.android.coriander_video.entity;


import ltd.android.coriander_video.utils.StringUtils;

import java.io.Serializable;

public class Tag implements Serializable {

    //columns START
    /**
     * id
     */
    private Integer id;
    /**
     * name
     */
    private String name;

    private String movieId;
    /**
     * category_id
     */
    private Integer categoryId;

    public Boolean isSelected = false;

    //columns END 数据库字段结束


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    //get and set
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = StringUtils.trim(name);
    }

    public String getName() {
        return this.name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}

