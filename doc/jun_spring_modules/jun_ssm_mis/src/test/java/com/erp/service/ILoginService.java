package com.erp.service;

import java.util.List;

import com.erp.viewModel.MenuModel;


/**
 * 类功能说明 TODO:
 * 类修改者	修改日期
 * 修改说明
 * <p>Title: LoginService.java</p>
 * <p>Description:福产流通科技</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company:福产流通科技</p>
 * @author Wujun
 * @date 2013-4-19 下午03:20:49
 * @version V1.0
 */
public interface ILoginService 
{
	List<MenuModel> findMenuList();
}
