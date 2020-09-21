package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;

/**
 * 图片VO
 *
 * @author TongWei.Chen 2017-05-17 18:30:18
 */
public class PictureListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
    /** 图片路径 */
    private String url;
    /** 图片宽度 */
    private Double width;
    /** 图片高度 */
    private Double height;
    /** 分类id */
    private Integer catalogId;
    /** 点赞总数 */
    private Long count;
    
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

    public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
}
