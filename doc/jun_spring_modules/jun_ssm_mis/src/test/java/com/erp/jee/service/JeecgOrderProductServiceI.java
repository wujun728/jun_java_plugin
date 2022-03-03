package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOrderProductEntity;
import com.erp.jee.page2.JeecgOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2011-12-31 16:22:59
 * @version V1.0   
 *
 */
public interface JeecgOrderProductServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOrderProductPage jeecgOrderProductPage);

	/**
	 * 添加
	 * 
	 * @param jeecgOrderProductPage
	 */
	public void add(JeecgOrderProductPage jeecgOrderProductPage);

	/**
	 * 修改
	 * 
	 * @param jeecgOrderProductPage
	 */
	public void update(JeecgOrderProductPage jeecgOrderProductPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOrderProduct
	 * @return
	 */
	public JeecgOrderProductEntity get(JeecgOrderProductPage jeecgOrderProductPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOrderProductEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOrderProductEntity> listAll(JeecgOrderProductPage jeecgOrderProductPage);

}
