package com.erp.service;

import com.erp.jee.pageModel.DataGrid;
import com.erp.page.DemoPage;

/**   
 * @Title: Service
 * @Description: 用户
 * @author Wujun
 * @date 2011-12-25 20:55:16
 * @version V1.0   
 *
 */
public interface DemoServiceI extends IBaseService {

	/**
	 * Spring Jdbc 分页
	 * @param dictParamPage
	 * @return
	 */
	public DataGrid listAllByJdbc(DemoPage demoPage);

}
