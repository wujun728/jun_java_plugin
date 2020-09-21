package com.opensource.nredis.proxy.monitor.service;

import java.util.List;

import com.opensource.nredis.proxy.monitor.model.PsMenu;

/**
* service interface
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
public interface IPsMenuService extends IBaseService<PsMenu>,IPaginationService<PsMenu>  {
	
	List<PsMenu> getMenusByUserId(PsMenu psMenu);
}

