/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.common.hibernate4.test.ModuleTreeDaoImpl.java
 * Class:			ModuleTreeDaoImpl
 * Date:			2012-12-25
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.common.hibernate4.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ketayao.common.hibernate4.Finder;
import com.ketayao.common.hibernate4.HibernateTreeDao;
import com.ketayao.common.hibernate4.Updater;
import com.ketayao.common.page.Pagination;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-25 上午11:22:30 
 */
@Repository
@Transactional(readOnly=true)
public class ModuleTreeDaoImpl extends HibernateTreeDao<ModuleTree, Long> implements ModuleTreeDao {

	/**   
	 * @param moduleTree
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#save(com.ketayao.common.hibernate4.test.ModuleTree)  
	 */
	@Transactional(readOnly=false)
	public ModuleTree save(ModuleTree moduleTree) {
		getSession().save(moduleTree);
		return moduleTree;
	}

	/**   
	 * @param updater
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#update(com.ketayao.common.hibernate4.Updater)  
	 */
	@Transactional(readOnly=false)
	public ModuleTree update(Updater<ModuleTree> updater) {
		ModuleTree moduleTree = super.updateByUpdater(updater);
		save(moduleTree);
		return moduleTree;
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#get(java.lang.Long)  
	 */
	public ModuleTree get(Long id) {
		return get(id, false);
	}

	/**   
	 * @param id
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#delete(java.lang.Long)  
	 */
	@Transactional(readOnly=false)
	public ModuleTree delete(Long id) {
		ModuleTree moduleTree = get(id);
		if (moduleTree != null) {
			getSession().delete(moduleTree);
		}
		return moduleTree;
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateBaseDao#getEntityClass()  
	 */
	@Override
	protected Class<ModuleTree> getEntityClass() {
		return ModuleTree.class;
	}

	/**   
	 * @param name
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#findByName(java.lang.String)  
	 */
	public List<ModuleTree> findByName(String name) {
		return findByProperty("name", name);
	}

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param cacheable
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#findPage(int, int, boolean)
	 */
	public Pagination<ModuleTree> findPage(int pageNo, int pageSize, boolean cacheable) {
		Finder finder = Finder.create("from ModuleTree bean");
		finder.setCacheable(cacheable);
	
		return super.find(finder, pageNo, pageSize);
	}

	/**   
	 * @param moduleTree
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#findAllTreeChildren(com.ketayao.common.hibernate4.test.ModuleTree)  
	 */
	public List<ModuleTree> findAllTreeChildren(ModuleTree moduleTree) {
		List<ModuleTree> list = (List<ModuleTree>)super.findAllTreeChildren(moduleTree);
		List<ModuleTree> tmp = new ArrayList<ModuleTree>(list);
		assembleNodes(moduleTree, tmp);
		
		return list;
	}

	private void assembleNodes(ModuleTree parent, List<ModuleTree> list) {
		if (list.size() == 0) {
			return;
		}
		
		List<ModuleTree> children = new ArrayList<ModuleTree>();
		for (ModuleTree t : list) {
			if (parent.getId().equals(t.getParentId())) {
				children.add(t);
			}
		}
		
		parent.setChildren(children);
		list.removeAll(children);
		for (ModuleTree moduleTree : children) {
			assembleNodes(moduleTree, list);
		}
	}

	/**   
	 * @param tree
	 * @return  
	 * @see com.ketayao.common.hibernate4.HibernateTreeDao#findAllLeaves(com.ketayao.common.hibernate4.HibernateTree)  
	 */
	@Override
	public List<ModuleTree> findAllLeaves(ModuleTree tree) {
		return super.findAllLeaves(tree);
	}

	/**   
	 * @return  
	 * @see com.ketayao.common.hibernate4.test.ModuleTreeDao#findAll()  
	 */
	@SuppressWarnings("unchecked")
	public List<ModuleTree> findAll() {
		return (List<ModuleTree>)super.createQuery("from ModuleTree").list();
	}
	
}
