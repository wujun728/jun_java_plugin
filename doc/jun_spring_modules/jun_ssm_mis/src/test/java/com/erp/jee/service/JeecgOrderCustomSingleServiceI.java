package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOrderCustomSingleEntity;
import com.erp.jee.page2.JeecgOrderCustomSinglePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2013-01-18 15:44:09
 * @version V1.0   
 *
 */
public interface JeecgOrderCustomSingleServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage);

	/**
	 * 添加
	 * 
	 * @param jeecgOrderCustomSinglePage
	 */
	public void add(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage);

	/**
	 * 修改
	 * 
	 * @param jeecgOrderCustomSinglePage
	 */
	public void update(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOrderCustomSingle
	 * @return
	 */
	public JeecgOrderCustomSingleEntity get(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOrderCustomSingleEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOrderCustomSingleEntity> listAll(JeecgOrderCustomSinglePage jeecgOrderCustomSinglePage);

}
