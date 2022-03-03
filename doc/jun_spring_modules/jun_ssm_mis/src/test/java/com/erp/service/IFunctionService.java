package com.erp.service;

import java.util.List;

import com.erp.model.Permission;
import com.erp.viewModel.TreeGridModel;
import com.erp.viewModel.TreeModel;

/**
* 类功能说明 TODO:程式管理service接口
* 类修改者
* 修改日期
* 修改说明
* <p>Title: FunctionService.java</p>
* <p>Description:福产流通科技</p>
* <p>Copyright: Copyright (c) 2006</p>
* <p>Company:福产流通科技有限公司</p>
* @author Wujun
* @date 2013-5-9 下午1:46:41
* @version V1.0
*/

public interface IFunctionService 
{


	List<TreeGridModel> findAllFunctionList(String id );

	boolean delFunction(Integer id );

	boolean persistenceFunction(List<Permission> list );

	List<TreeModel> findAllFunctionList();

	boolean persistenceFunction(Permission permission );
	
}
