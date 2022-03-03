package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOrderCustomEntity;
import com.erp.jee.page2.JeecgOrderCustomPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单客户明细
 * @author Wujun
 * @date 2011-12-31 16:22:58
 * @version V1.0   
 *
 */
public interface JeecgOrderCustomServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOrderCustomPage jeecgOrderCustomPage);

	/**
	 * 添加
	 * 
	 * @param jeecgOrderCustomPage
	 */
	public void add(JeecgOrderCustomPage jeecgOrderCustomPage);

	/**
	 * 修改
	 * 
	 * @param jeecgOrderCustomPage
	 */
	public void update(JeecgOrderCustomPage jeecgOrderCustomPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOrderCustom
	 * @return
	 */
	public JeecgOrderCustomEntity get(JeecgOrderCustomPage jeecgOrderCustomPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOrderCustomEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOrderCustomEntity> listAll(JeecgOrderCustomPage jeecgOrderCustomPage);

}
