package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.GbuyOrderCustomEntity;
import com.erp.jee.page2.GbuyOrderCustomPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2011-12-19 13:09:30
 * @version V1.0   
 *
 */
public interface GbuyOrderCustomServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(GbuyOrderCustomPage gbuyOrderCustomPage);

	/**
	 * 添加
	 * 
	 * @param gbuyOrderCustomPage
	 */
	public void add(GbuyOrderCustomPage gbuyOrderCustomPage);

	/**
	 * 修改
	 * 
	 * @param gbuyOrderCustomPage
	 */
	public void update(GbuyOrderCustomPage gbuyOrderCustomPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param GbuyOrderCustom
	 * @return
	 */
	public GbuyOrderCustomEntity get(GbuyOrderCustomPage gbuyOrderCustomPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public GbuyOrderCustomEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<GbuyOrderCustomEntity> listAll(GbuyOrderCustomPage gbuyOrderCustomPage);

}
