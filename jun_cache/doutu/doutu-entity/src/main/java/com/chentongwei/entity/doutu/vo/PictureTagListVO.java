package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;

/**
 * 根据图片标签名称查询列表VO
 * 
 * @author TongWei.Chen 2017年5月29日22:40:28
 */
public class PictureTagListVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 图片id */
	private Long pictureId;
	/** 图片url */
	private String url;
	/** 图片宽度 */
	private Double width;
	/** 图片高度 */
	private Double height;

	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
}