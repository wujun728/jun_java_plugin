package com.opensource.nredis.proxy.monitor.dao;

import java.util.List;

import com.opensource.nredis.proxy.monitor.model.PsMenu;


/**
* dao
*
* @author liubing
* @date 2016/12/12 12:20
* @version v1.0.0
*/
public interface IPsMenuDao extends IMyBatisRepository<PsMenu>,IPaginationDao<PsMenu> {
	
	/**
	 * 根据UserId查询菜单
	 * @param userIds
	 * @return
	 */
	List<PsMenu> getMenusByUserId(PsMenu psMenu);
}
