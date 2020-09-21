package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;

/**
 * 图片详情VO
 * 
 * @author TongWei.Chen 2017年5月29日23:34:08
 */
public class PictureDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 /** id */
    private Long id;
    /** 图片路径 */
    private String url;
    /** 分类id */
    private Integer catalogId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}
    
}
