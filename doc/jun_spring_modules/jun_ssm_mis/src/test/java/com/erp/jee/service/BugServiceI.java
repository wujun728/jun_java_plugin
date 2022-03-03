package com.erp.jee.service;

import com.erp.jee.model.Tbug;
import com.erp.jee.pageModel.Bug;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**
 * Bug Service
 * 
 * @author Wujun
 * 
 */
public interface BugServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(Bug bug);

	/**
	 * 添加
	 * 
	 * @param bug
	 */
	public void add(Bug bug);

	/**
	 * 修改
	 * 
	 * @param bug
	 */
	public void update(Bug bug);

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param bug
	 * @return
	 */
	public Tbug get(Bug bug);

}
