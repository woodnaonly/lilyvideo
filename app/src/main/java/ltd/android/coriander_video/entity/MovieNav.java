package ltd.android.coriander_video.entity;


import java.io.Serializable;


public class MovieNav implements Serializable{
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	/**
	 * nav_id
	 */
	private Integer navId;
	/**
	 * movie_id
	 */
	private Integer movieId;
	//columns END 数据库字段结束
	
	//get and set
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setNavId(Integer navId) {
		this.navId = navId;
	}
	
	public Integer getNavId() {
		return this.navId;
	}
	
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	
	public Integer getMovieId() {
		return this.movieId;
	}
	
}

