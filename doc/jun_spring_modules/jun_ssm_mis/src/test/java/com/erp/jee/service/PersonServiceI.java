package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.PersonEntity;
import com.erp.jee.page2.PersonPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 用户
 * @author Wujun
 * @date 2013-01-17 11:41:26
 * @version V1.0   
 *
 */
public interface PersonServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(PersonPage personPage);

	/**
	 * 添加
	 * 
	 * @param personPage
	 */
	public void add(PersonPage personPage);

	/**
	 * 修改
	 * 
	 * @param personPage
	 */
	public void update(PersonPage personPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param Person
	 * @return
	 */
	public PersonEntity get(PersonPage personPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public PersonEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<PersonEntity> listAll(PersonPage personPage);

}
