package ltd.android.coriander_video.net.http.response;

import ltd.android.coriander_video.dto.ColumnMovieDTO;
import ltd.android.coriander_video.dto.MovieDTO;
import ltd.android.coriander_video.entity.Ad;
import ltd.android.coriander_video.entity.MovieClass;

import java.util.List;

/**
 * @author by 梁馨 on 2019/3/2.
 */
public class HomeRespone {
    public List<Ad> adList;
    public List<MovieDTO> newmovList;
    public List<ColumnMovieDTO> columnsList;
    public List<MovieDTO> hotMovList;
    public List<MovieDTO> guessLikeList;
    public List<MovieClass> movieClassList;
}
