package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOrderProductSingleEntity;
import com.erp.jee.page2.JeecgOrderProductSinglePage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2013-01-18 15:44:10
 * @version V1.0   
 *
 */
public interface JeecgOrderProductSingleServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOrderProductSinglePage jeecgOrderProductSinglePage);

	/**
	 * 添加
	 * 
	 * @param jeecgOrderProductSinglePage
	 */
	public void add(JeecgOrderProductSinglePage jeecgOrderProductSinglePage);

	/**
	 * 修改
	 * 
	 * @param jeecgOrderProductSinglePage
	 */
	public void update(JeecgOrderProductSinglePage jeecgOrderProductSinglePage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOrderProductSingle
	 * @return
	 */
	public JeecgOrderProductSingleEntity get(JeecgOrderProductSinglePage jeecgOrderProductSinglePage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOrderProductSingleEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOrderProductSingleEntity> listAll(JeecgOrderProductSinglePage jeecgOrderProductSinglePage);

}
