package com.erp.viewModel;

import java.util.ArrayList;
import java.util.List;
/**
 * 类功能说明 TODO:Exception工具类
 * 类修改者	修改日期
 * 修改说明
 * <p>Title: BaseService.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company:福产流通科技</p>
 * @author Wujun
 * @date 2013-4-19 下午03:18:05
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class GridModel {
	private List  rows= new ArrayList();
	private Long total=0L;
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
}
