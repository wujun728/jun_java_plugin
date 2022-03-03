package com.erp.jee.service;

import com.erp.jee.model.Tonline;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.pageModel.Online;
import com.erp.service.IBaseService;

/**
 * 在线用户Service
 * 
 * @author Wujun
 * 
 */
public interface OnlineServiceI extends IBaseService {

	/**
	 * 更新或插入用户在线列表
	 * 
	 * @param online
	 */
	public void updateOnline(Tonline online);

	/**
	 * 删除用户在线列表
	 * 
	 * @param loginName
	 * @param ip
	 */
	public void deleteOnline(String loginName, String ip);

	/**
	 * 获得用户在线列表datagrid
	 * 
	 * @param online
	 * @return
	 */
	public DataGrid datagrid(Online online);

}
