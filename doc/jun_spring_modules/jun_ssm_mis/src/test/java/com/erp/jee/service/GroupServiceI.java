package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.GroupEntity;
import com.erp.jee.page2.GroupPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 组织机构
 * @author Wujun
 * @date 2013-01-18 12:18:08
 * @version V1.0   
 *
 */
public interface GroupServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(GroupPage groupPage);

	/**
	 * 添加
	 * 
	 * @param groupPage
	 */
	public void add(GroupPage groupPage);

	/**
	 * 修改
	 * 
	 * @param groupPage
	 */
	public void update(GroupPage groupPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param Group
	 * @return
	 */
	public GroupEntity get(GroupPage groupPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public GroupEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<GroupEntity> listAll(GroupPage groupPage);

}
