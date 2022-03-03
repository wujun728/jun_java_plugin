package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOneTestEntity;
import com.erp.jee.page2.JeecgOneTestPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 单表模型Test
 * @author Wujun
 * @date 2011-12-31 14:18:16
 * @version V1.0   
 *
 */
public interface JeecgOneTestServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOneTestPage jeecgOneTestPage);

	/**
	 * 添加
	 * 
	 * @param jeecgOneTestPage
	 */
	public void add(JeecgOneTestPage jeecgOneTestPage);

	/**
	 * 修改
	 * 
	 * @param jeecgOneTestPage
	 */
	public void update(JeecgOneTestPage jeecgOneTestPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOneTest
	 * @return
	 */
	public JeecgOneTestEntity get(JeecgOneTestPage jeecgOneTestPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOneTestEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOneTestEntity> listAll(JeecgOneTestPage jeecgOneTestPage);

}
