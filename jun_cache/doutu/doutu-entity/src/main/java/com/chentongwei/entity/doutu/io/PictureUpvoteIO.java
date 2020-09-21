package com.chentongwei.entity.doutu.io;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 图片点赞IO
 * 
 * @author TongWei.Chen 2017-5-30 18:10:22
 */
public class PictureUpvoteIO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 主键id */
    private Long id;
    /** 图片id */
    @NotNull
    private Long pictureId;
    /** 用户id */
    @NotNull
    private Long userId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
}
