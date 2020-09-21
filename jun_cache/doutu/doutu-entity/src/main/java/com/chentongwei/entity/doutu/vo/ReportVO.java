package com.chentongwei.entity.doutu.vo;

import com.chentongwei.entity.doutu.po.Report;

/**
 * 举报VO
 * 
 * @author TongWei.Chen 2017年5月30日01:28:34
 */
public class ReportVO extends Report {

	private static final long serialVersionUID = 1L;
	
	/** 图片url */
	private String pictureUrl;
	
	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
}
