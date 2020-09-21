package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.entity.Page;

/**
 * 根据标签名称模糊查询图片列表的IO
 * 
 * @author TongWei.Chen 2017年5月29日22:40:20
 */
public class PictureTagListIO extends Page {

	private static final long serialVersionUID = 1L;

	/** 标签名称 */
	private String tagName;

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
