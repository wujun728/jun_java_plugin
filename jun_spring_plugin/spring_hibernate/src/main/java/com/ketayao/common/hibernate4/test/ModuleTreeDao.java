/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.ModuleTreeDAO.java
 * Class:			ModuleTreeDAO
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.hibernate4.test;

import java.util.List;

import com.ketayao.common.hibernate4.Updater;
import com.ketayao.common.page.Pagination;


/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-25 上午11:19:23 
 */

public interface ModuleTreeDao {
	ModuleTree save(ModuleTree moduleTree);
	
	ModuleTree update(Updater<ModuleTree> updater);
	
	ModuleTree get(Long id);
	
	ModuleTree delete(Long id);
	
	List<ModuleTree> findAll();
	
	List<ModuleTree> findByName(String name);
	
	Pagination<ModuleTree> findPage(int pageNo, int pageSize, boolean cacheable);
	
	List<ModuleTree> findAllTreeChildren(ModuleTree moduleTree);
	
	List<ModuleTree> findAllLeaves(ModuleTree moduleTree); 
}
