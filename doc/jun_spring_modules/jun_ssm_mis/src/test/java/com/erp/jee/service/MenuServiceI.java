package com.erp.jee.service;

import java.util.List;

import com.erp.jee.pageModel.Menu;
import com.erp.jee.pageModel.TreeNode;
import com.erp.service.IBaseService;

/**
 * 菜单Service
 * 
 * @author Wujun
 * 
 */
public interface MenuServiceI extends IBaseService {

	/**
	 * 获得菜单treegrid
	 * 
	 * @param menu
	 * @return
	 */
	public List<Menu> treegrid(Menu menu);

	/**
	 * 获取菜单树
	 * 
	 * @param auth
	 * @param b
	 *            是否递归子节点
	 * @return
	 */
	public List<TreeNode> tree(Menu menu, Boolean b);

	/**
	 * 编辑菜单
	 * 
	 * @param menu
	 */
	public void edit(Menu menu);

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 */
	public void add(Menu menu);

	/**
	 * 删除菜单
	 * 
	 * @param menu
	 */
	public void delete(Menu menu);

}
