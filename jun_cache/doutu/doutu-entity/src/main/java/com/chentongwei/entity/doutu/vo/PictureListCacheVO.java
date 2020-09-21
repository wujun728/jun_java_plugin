package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;
import java.util.List;

public class PictureListCacheVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 图片总数量 */
	private long total;
	/** 图片集合 */
	private List<PictureListVO> list;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}

	public List<PictureListVO> getList() {
		return list;
	}

	public void setList(List<PictureListVO> list) {
		this.list = list;
	}
}
