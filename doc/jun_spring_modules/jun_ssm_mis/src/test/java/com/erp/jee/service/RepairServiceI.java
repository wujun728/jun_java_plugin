package com.erp.jee.service;

import com.erp.service.IBaseService;

/**
 * 修复数据库Service
 * 
 * @author Wujun
 * 
 */
public interface RepairServiceI extends IBaseService {

	/**
	 * 修复数据库
	 */
	public void repair();

	/**
	 * 先清空数据库，然后再修复数据库
	 */
	public void deleteAndRepair();

}
