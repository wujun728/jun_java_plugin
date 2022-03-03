package org.springrain.system.service;

import java.util.List;

import org.springrain.system.entity.Menu;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see org.springrain.springrain.service.Menu
 */
public interface IMenuService extends IBaseSpringrainService {
/**
	 * 保存 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	String saveMenu(Menu entity) throws Exception;
	
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	String deleteMenuById(String menuId) throws Exception;
	
	
	/**
	 * 修改或者保存,根据id是否为空判断
	 * @param entity
	 * @return
	 * @throws Exception
	 */
    String saveorupdateMenu(Menu entity) throws Exception;
	 /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
	Integer updateMenu(Menu entity) throws Exception;
	/**
	 * 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Menu findMenuById(Object id) throws Exception;
	
	
	/**
	 * 
	 * @Title: findListById
	 * @Description: 根据ID查找
	 * @param id
	 * @return
	 * @throws Exception
	 * @return List<Menu>
	 * @throws
	 */
	List<Menu> findListById(Object id) throws Exception;
	/**
	 * 根据pageurl查询菜单名称
	 * @param pageurl
	 * @return
	 * @throws Exception
	 */
	String getNameByPageurl(String pageurl) throws Exception;
	
	
}
