package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.GbuyOrderProductEntity;
import com.erp.jee.page2.GbuyOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单产品明细
 * @author Wujun
 * @date 2011-12-19 13:09:31
 * @version V1.0   
 *
 */
public interface GbuyOrderProductServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(GbuyOrderProductPage gbuyOrderProductPage);

	/**
	 * 添加
	 * 
	 * @param gbuyOrderProductPage
	 */
	public void add(GbuyOrderProductPage gbuyOrderProductPage);

	/**
	 * 修改
	 * 
	 * @param gbuyOrderProductPage
	 */
	public void update(GbuyOrderProductPage gbuyOrderProductPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param GbuyOrderProduct
	 * @return
	 */
	public GbuyOrderProductEntity get(GbuyOrderProductPage gbuyOrderProductPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public GbuyOrderProductEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<GbuyOrderProductEntity> listAll(GbuyOrderProductPage gbuyOrderProductPage);

}
