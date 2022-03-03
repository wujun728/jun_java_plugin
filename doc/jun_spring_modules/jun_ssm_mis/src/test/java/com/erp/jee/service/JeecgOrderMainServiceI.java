package com.erp.jee.service;

import java.util.List;

import com.erp.jee.entity.JeecgOrderMainEntity;
import com.erp.jee.page2.JeecgOrderCustomPage;
import com.erp.jee.page2.JeecgOrderMainPage;
import com.erp.jee.page2.JeecgOrderProductPage;
import com.erp.jee.pageModel.DataGrid;
import com.erp.service.IBaseService;

/**   
 * @Title: Service
 * @Description: 订单主数据
 * @author Wujun
 * @date 2011-12-31 16:23:01
 * @version V1.0   
 *
 */
public interface JeecgOrderMainServiceI extends IBaseService {

	/**
	 * 获得数据表格
	 * 
	 * @param bug
	 * @return
	 */
	public DataGrid datagrid(JeecgOrderMainPage jeecgOrderMainPage);

	/**
	 * 添加
	 * 
	 * @param jeecgOrderMainPage
	 */
	public void add(JeecgOrderMainPage jeecgOrderMainPage);

	
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(JeecgOrderMainPage jeecgOrderMainPage,List<JeecgOrderCustomPage> jeecgOrderCustomList,List<JeecgOrderProductPage> jeecgOrderProductList)  throws Exception ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void editMain(JeecgOrderMainPage jeecgOrderMainPage,List<JeecgOrderCustomPage> jeecgOrderCustomList,List<JeecgOrderProductPage> jeecgOrderProductList)  throws Exception ;
	
	
	/**
	 * 修改
	 * 
	 * @param jeecgOrderMainPage
	 */
	public void update(JeecgOrderMainPage jeecgOrderMainPage) throws Exception;

	/**
	 * 删除
	 * 
	 * @param ids
	 */
	public void delete(String ids);

	/**
	 * 获得
	 * 
	 * @param JeecgOrderMain
	 * @return
	 */
	public JeecgOrderMainEntity get(JeecgOrderMainPage jeecgOrderMainPage);
	
	
	/**
	 * 获得
	 * 
	 * @param obid
	 * @return
	 */
	public JeecgOrderMainEntity get(String obid);
	
	/**
	 * 获取所有数据
	 */
	public List<JeecgOrderMainEntity> listAll(JeecgOrderMainPage jeecgOrderMainPage);
	
	/**根据主表Key,查询子表明细：订单客户明细*/
	public List<JeecgOrderCustomPage> getJeecgOrderCustomListByFkey(String obid,String goOrderCode);
	/**根据主表Key,查询子表明细：订单产品明细*/
	public List<JeecgOrderProductPage> getJeecgOrderProductListByFkey(String obid,String goOrderCode);

}
