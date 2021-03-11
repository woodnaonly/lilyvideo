package ltd.android.coriander_video.dto;


import java.io.Serializable;
import java.util.List;

public class ColumnMovieDTO implements Serializable {

    private static final long serialVersionUID = 205108001620041127L;

    private Integer navId;
    private String name;
    private String navImage;
    private String cover;
    public String intro;

    private List<MovieDTO> movies;

    public Integer getNavId() {
        return navId;
    }

    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNavImage() {
        return navImage;
    }

    public void setNavImage(String navImage) {
        this.navImage = navImage;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }


    public ColumnNavDTO.Nav transformNav() {
        ColumnNavDTO.Nav nav = new ColumnNavDTO.Nav();
        nav.setId(this.navId);
        nav.setNavImage(this.navImage);
        nav.setIntro(this.intro);
        nav.setNavName(this.name);
        return nav;
    }
}
