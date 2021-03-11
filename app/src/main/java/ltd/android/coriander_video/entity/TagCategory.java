package ltd.android.coriander_video.entity;


import ltd.android.coriander_video.utils.StringUtils;

import java.io.Serializable;

public class TagCategory implements Serializable{
	
	//columns START
	/**
	 * id
	 */
	private Integer id;
	/**
	 * name
	 */
	private String name;

	private Integer rank;
	//columns END 数据库字段结束
	
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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}

