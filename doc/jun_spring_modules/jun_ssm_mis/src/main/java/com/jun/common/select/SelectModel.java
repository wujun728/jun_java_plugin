package com.jun.common.select;

import com.jun.common.json.JSONModel;

/**
 * @author Wujun
 * @createTime   2011-11-27 上午10:45:00
 */
public class SelectModel extends JSONModel{
	private SelectBO selectBO;

	public SelectBO getSelectBO() {
		if(null == selectBO){
			selectBO = new SelectBO();
		}
		return selectBO;
	}

	public void setSelectBO(SelectBO selectBO) {
		this.selectBO = selectBO;
	}
}
